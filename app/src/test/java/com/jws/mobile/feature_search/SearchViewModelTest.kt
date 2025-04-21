package com.jws.mobile.feature_search

import com.jws.mobile.feature_search.domain.Search
import com.jws.mobile.feature_search.presentation.viewmodel.SearchUiEffect
import com.jws.mobile.feature_search.presentation.viewmodel.SearchUiEvent
import com.jws.mobile.feature_search.presentation.viewmodel.SearchViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class SearchViewModelTest: SearchBaseViewModelTest() {

    @Test
    fun `onEvent OnCancelClicked emits OnCancelClicked effect`() = runTest {
        val effect = async {
            viewModel.eventFlow.first { it is SearchUiEffect.OnCancelClicked }
        }

        viewModel.onEvent(SearchUiEvent.OnCancelClicked)

        assert(effect.await() is SearchUiEffect.OnCancelClicked)
    }

    @Test
    fun `init fetches latest search and updates uiState`() = runTest {
        val expectedSearch = listOf(Search("ultima busqueda"))
        coEvery { getRecentSearchUseCase() } returns expectedSearch

        val viewModel = SearchViewModel(getRecentSearchUseCase, addRecentSearchUseCase, dispatcherProvider)

        assertEquals(expectedSearch, viewModel.uiState.value.search)
    }

    @Test
    fun `onEvent RequestNavigateToPreview emits NavigateToPreview and saves search`() = runTest {
        val searchTerm = "nuevo término"

        val effect = async {
            viewModel.eventFlow.first { it is SearchUiEffect.NavigateToPreview }
        }

        viewModel.onEvent(SearchUiEvent.RequestNavigateToPreview(searchTerm))

        assert(effect.await() == SearchUiEffect.NavigateToPreview(searchTerm))
        coVerify { addRecentSearchUseCase(searchTerm) }
    }

}