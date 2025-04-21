package com.jws.mobile.feature_search.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jws.mobile.core.di.DispatcherProvider
import com.jws.mobile.feature_search.domain.AddRecentSearchUseCase
import com.jws.mobile.feature_search.domain.GetRecentSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getRecentSearchUseCase: GetRecentSearchUseCase,
    private val addRecentSearchUseCase: AddRecentSearchUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    private val _eventFlow = MutableSharedFlow<SearchUiEffect>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchLatestSearch()
    }

    private fun fetchLatestSearch() = viewModelScope.launch(dispatcherProvider.io) {
        _uiState.value = _uiState.value.copy(search = getRecentSearchUseCase())
    }

    fun onEvent(event: SearchUiEvent) = when (event) {
        is SearchUiEvent.RequestNavigateToPreview -> navPreview(event.value)
        is SearchUiEvent.OnCancelClicked -> cancelClick()
    }

    private fun cancelClick() = emitEffect(SearchUiEffect.OnCancelClicked)

    private fun navPreview(value: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            addRecentSearchUseCase(value)
            emitEffect(SearchUiEffect.NavigateToPreview(value))
        }
    }

    private fun emitEffect(effect: SearchUiEffect) {
        viewModelScope.launch(dispatcherProvider.mainImmediate) { _eventFlow.emit(effect) }
    }

}