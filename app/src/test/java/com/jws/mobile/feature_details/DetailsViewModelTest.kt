package com.jws.mobile.feature_details

import com.jws.mobile.core.features.Picture
import com.jws.mobile.core.utils.Resource
import com.jws.mobile.feature_detail.domain.Detail
import com.jws.mobile.feature_detail.presentation.viewmodel.DetailUiEffect
import com.jws.mobile.feature_detail.presentation.viewmodel.DetailUiEvent
import io.mockk.coEvery
import io.mockk.coVerify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailsViewModelTest: DetailsBaseViewModelTest() {

    @Test
    fun `when RequestNavigateToSearch is triggered, it emits NavigateToSearch`() = runTest {
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.eventFlow.collectLatest {
                assertEquals(DetailUiEffect.NavigateToSearch, it)
                cancel()
            }
        }

        viewModel.onEvent(DetailUiEvent.RequestNavigateToSearch)
        advanceUntilIdle()
        job.join()
    }

    @Test
    fun `when OnBackClicked is triggered, it emits OnBackClicked`() = runTest {
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.eventFlow.collectLatest {
                assertEquals(DetailUiEffect.OnBackClicked, it)
                cancel()
            }
        }

        viewModel.onEvent(DetailUiEvent.OnBackClicked)
        advanceUntilIdle()
        job.join()
    }

    @Test
    fun `when FetchDetails is successful, it updates uiState with the details and sets isLoading to false`() = runTest {
        val detail = detailFactory(id = "MLA123")
        coEvery { fetchDetailByIdUseCase("MLA123") } returns Resource.Success(detail)

        viewModel.onEvent(DetailUiEvent.FetchDetails("MLA123"))
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(detail, state.detail)
    }

    @Test
    fun `when FetchDetails fails, it emits ShowToastError`() = runTest {
        coEvery { fetchDetailByIdUseCase("MLA123") } returns Resource.Error(null, "Error")

        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.eventFlow.collectLatest {
                assertTrue(it is DetailUiEffect.ShowToastError)
                assertEquals("Error", (it as DetailUiEffect.ShowToastError).error)
                cancel()
            }
        }

        viewModel.onEvent(DetailUiEvent.FetchDetails("MLA123"))
        advanceUntilIdle()
        job.join()
    }

    @Test
    fun `when hasFetched is true, it does not call fetchDetailByIdUseCase again`() = runTest {
        val detail = detailFactory()
        coEvery { fetchDetailByIdUseCase("MLA123") } returns Resource.Success(detail)

        viewModel.onEvent(DetailUiEvent.FetchDetails("MLA123"))
        advanceUntilIdle()

        viewModel.onEvent(DetailUiEvent.FetchDetails("MLA123"))
        advanceUntilIdle()

        coVerify(exactly = 1) { fetchDetailByIdUseCase("MLA123") }
    }

    private fun detailFactory(
        id: String = "MLA123",
        title: String = "Producto de prueba",
        price: Int = 1000,
        basePrice: Int = 1200,
        availableQuantity: Int = 10,
        initialQuantity: Int = 20,
        condition: String = "new",
        pictures: List<Picture> = listOf(Picture(id = "1" ,url = "http://example.com/img1.jpg")),
        warranty: String = "Garantia de 6 meses",
        acceptsMercadopago: Boolean = true,
        buyingMode: String = "buy_it_now",
        internationalDeliveryMode: String = "none"
    ): Detail {
        return Detail(
            id = id,
            title = title,
            price = price,
            basePrice = basePrice,
            availableQuantity = availableQuantity,
            initialQuantity = initialQuantity,
            condition = condition,
            pictures = pictures,
            warranty = warranty,
            acceptsMercadopago = acceptsMercadopago,
            buyingMode = buyingMode,
            internationalDeliveryMode = internationalDeliveryMode
        )
    }

}