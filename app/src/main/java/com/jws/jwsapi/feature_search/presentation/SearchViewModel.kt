package com.jws.jwsapi.feature_search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jws.jwsapi.feature_preview.domain.FetchItemsBySellerUseCase
import com.jws.jwsapi.feature_preview.domain.FetchPreviewsByIdUseCase
import com.jws.jwsapi.feature_search.domain.Search
import com.jws.jwsapi.shared.DispatcherProvider
import com.jws.jwsapi.shared.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    private val _eventFlow = MutableSharedFlow<SearchUiEffect>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        _uiState.value = _uiState.value.copy(search = listOf(Search("iphone"), Search("auto"), Search("zapatillas")))
    }

    fun onEvent(event: SearchUiEvent) {
        when (event) {
            is SearchUiEvent.RequestNavigateToPreview -> navPreview(event.value)
        }
    }

    private fun navPreview(value: String) {
        emitEffect(SearchUiEffect.NavigateToPreview(value))
    }

    private fun navDetails() {
//        findNavController().navigate(R.id.action_previewFragment_to_searchFragment)
    }

    /*private fun loadItems() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = fetchItemsBySellerUseCase()) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    val items = result.data?.results.orEmpty()
                    loadPreviews(items)
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    emitEffect(SearchUiEffect.ShowToastError(result.error ?: "Error desconocido"))
                }

                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }

    private fun loadPreviews(items: List<String>) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = fetchPreviewsByIdUseCase(items.joinToString(","))) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    result.data?.let { items ->
                        _uiState.value = _uiState.value.copy(previewList = items)
                    }
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    emitEffect(SearchUiEffect.ShowToastError(result.error ?: "Error desconocido"))
                }

                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }
*/
    private fun emitEffect(effect: SearchUiEffect) = viewModelScope.launch(dispatcherProvider.mainImmediate) {
        _eventFlow.emit(effect)
    }

}