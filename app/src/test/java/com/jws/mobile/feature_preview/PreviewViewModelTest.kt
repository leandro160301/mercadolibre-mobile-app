package com.jws.mobile.feature_preview

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.jws.mobile.core.utils.Resource
import com.jws.mobile.feature_preview.domain.items.ItemsResponse
import com.jws.mobile.feature_preview.presentation.viewmodel.PreviewUiEffect
import com.jws.mobile.feature_preview.presentation.viewmodel.PreviewUiEvent
import io.mockk.coEvery
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class PreviewViewModelTest: PreviewBaseViewModelTest() {

    @Test
    fun `when RequestNavigateToSearch is triggered, it emits NavigateToSearch`() = runTest {
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.eventFlow.collectLatest {
                assertEquals(PreviewUiEffect.NavigateToSearch, it)
                cancel()
            }
        }

        viewModel.onEvent(PreviewUiEvent.RequestNavigateToSearch)
        advanceUntilIdle()
        job.join()
    }

    @Test
    fun `when FetchItems is triggered with results, it updates the uiState and calls loadPreviews`() = runTest {
        val itemsResponse = ItemsResponse("1", results = listOf("id1", "id2"))
        coEvery { fetchItemsBySellerUseCase("query") } returns Resource.Success(itemsResponse)
        coEvery { fetchPreviewsByIdUseCase(any()) } returns Resource.Success(emptyList())

        viewModel.onEvent(PreviewUiEvent.FetchItems("query"))
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals("query", state.query)
        assertTrue(state.deleteButtonIsVisible)
    }

    @Test
    fun `when fetchItems fails, it emits ShowToastError`() = runTest {
        coEvery { fetchItemsBySellerUseCase("query") } returns Resource.Error(null, "Error")

        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.eventFlow.collectLatest {
                assertTrue(it is PreviewUiEffect.ShowToastError)
                assertEquals("Error", (it as PreviewUiEffect.ShowToastError).error)
                cancel()
            }
        }

        viewModel.onEvent(PreviewUiEvent.FetchItems("query"))
        advanceUntilIdle()
        job.join()
    }

    @Test
    fun `when the OnDeletSearchClicked event is triggered, it emits OnDeleteSearchClicked`() = runTest {
        viewModel.onEvent(PreviewUiEvent.OnDeleteSearchClicked)
        val event = viewModel.eventFlow.first()
        assertTrue(event is PreviewUiEffect.OnDeleteSearchClicked)
    }

    @Test
    fun `when RequestNavigateToDetails is triggered, it emits NavigateToDetails with the correct id`() = runTest {
        val productId = "MLA12345"
        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.eventFlow.collectLatest {
                assertTrue(it is PreviewUiEffect.NavigateToDetails)
                assertEquals(productId, (it as PreviewUiEffect.NavigateToDetails).productId)
                cancel()
            }
        }

        viewModel.onEvent(PreviewUiEvent.RequestNavigateToDetails(productId))
        advanceUntilIdle()
        job.join()
    }

    @Test
    fun `RequestNavigateToDetails emits NavigateToDetails`() = runTest {
        val productId = "MLA123"

        viewModel.eventFlow.test {
            viewModel.onEvent(PreviewUiEvent.RequestNavigateToDetails(productId))
            advanceUntilIdle()

            val effect = awaitItem()
            assertThat(effect).isEqualTo(PreviewUiEffect.NavigateToDetails(productId))
        }
    }

    @Test
    fun `when query is null, deleteButtonIsVisible is false`() = runTest {
        coEvery { fetchItemsBySellerUseCase(null) } returns Resource.Success(ItemsResponse("1", emptyList()))
        coEvery { fetchPreviewsByIdUseCase(any()) } returns Resource.Success(emptyList())

        viewModel.onEvent(PreviewUiEvent.FetchItems(null))
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.deleteButtonIsVisible)
    }

    @Test
    fun `when fetchPreviews fails, emits ShowToastError`() = runTest {
        val itemsResponse = ItemsResponse("1", results = listOf("id1"))
        coEvery { fetchItemsBySellerUseCase("query") } returns Resource.Success(itemsResponse)
        coEvery { fetchPreviewsByIdUseCase(any()) } returns Resource.Error(null, "Preview error")

        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.eventFlow.collectLatest {
                assertTrue(it is PreviewUiEffect.ShowToastError)
                assertEquals("Preview error", (it as PreviewUiEffect.ShowToastError).error)
                cancel()
            }
        }

        viewModel.onEvent(PreviewUiEvent.FetchItems("query"))
        advanceUntilIdle()
        job.join()
    }

}