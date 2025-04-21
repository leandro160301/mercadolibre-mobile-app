package com.jws.mobile.feature_details

import com.jws.mobile.core.utils.Resource
import com.jws.mobile.feature_detail.domain.DetailRepository
import com.jws.mobile.feature_detail.domain.FetchDetailByIdUseCase
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
class FetchDetailByIdUseCaseTest {

    private lateinit var useCase: FetchDetailByIdUseCase
    private val repository: DetailRepository = mockk()

    @Before
    fun setUp() {
        useCase = FetchDetailByIdUseCase(repository)
    }

    @Test
    fun `when repository returns success, use case returns success`() = runTest {
        val mockDetail = DetailFactory.getData()
        coEvery { repository.getItemsDetails("MLA123") } returns Resource.Success(mockDetail)

        val result = useCase("MLA123")

        assertTrue(result is Resource.Success)
        assertEquals(mockDetail, (result as Resource.Success).data)
        coVerify { repository.getItemsDetails("MLA123") }
    }

    @Test
    fun `when repository returns error, use case returns error`() = runTest {
        val errorMessage = "Error al obtener detalle"
        coEvery { repository.getItemsDetails("MLA123") } returns Resource.Error(null, errorMessage)

        val result = useCase("MLA123")

        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, (result as Resource.Error).error)
        coVerify { repository.getItemsDetails("MLA123") }
    }
}