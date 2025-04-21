package com.jws.mobile.feature_preview

import com.jws.mobile.core.utils.Resource
import com.jws.mobile.feature_preview.domain.FetchItemsBySellerUseCase
import com.jws.mobile.feature_preview.domain.PreviewRepository
import com.jws.mobile.feature_preview.domain.items.ItemsResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchItemsBySellerUseCaseTest {

    private lateinit var useCase: FetchItemsBySellerUseCase
    private val repository: PreviewRepository = mockk()

    @Before
    fun setUp() {
        useCase = FetchItemsBySellerUseCase(repository)
    }

    @Test
    fun `when repository returns success, use case returns success`() = runTest {
        val mockResponse = ItemsResponse(sellerId = "", results = emptyList())
        coEvery { repository.getItemsBySeller("iphone") } returns Resource.Success(mockResponse)

        val result = useCase("iphone")

        assertTrue(result is Resource.Success)
        assertEquals(mockResponse, (result as Resource.Success).data)
        coVerify { repository.getItemsBySeller("iphone") }
    }

    @Test
    fun `when repository returns error, use case returns error`() = runTest {
        val errorMessage = "Something went wrong"
        coEvery { repository.getItemsBySeller("iphone") } returns Resource.Error(null, errorMessage)

        val result = useCase("iphone")

        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, (result as Resource.Error).error)
        coVerify { repository.getItemsBySeller("iphone") }
    }
}