package com.jws.jwsapi.feature_preview.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jws.jwsapi.feature_preview.domain.FetchItemsBySellerUseCase
import com.jws.jwsapi.feature_preview.domain.FetchPreviewsByIdUseCase
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
class PreviewViewModel @Inject constructor(
    private val fetchItemsBySellerUseCase: FetchItemsBySellerUseCase,
    private val fetchPreviewsByIdUseCase: FetchPreviewsByIdUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(PreviewUiState())
    val uiState: StateFlow<PreviewUiState> = _uiState

    private val _eventFlow = MutableSharedFlow<PreviewUiEffect>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        loadItems()
    }

    fun onEvent(event: PreviewUiEvent) {
        when (event) {
            is PreviewUiEvent.FetchItems -> loadItems()
            is PreviewUiEvent.UpdateSystem -> TODO()
        }
    }

    private fun loadItems() {
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
                    emitEffect(PreviewUiEffect.ShowToastError(result.error ?: "Error desconocido"))
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
                    emitEffect(PreviewUiEffect.ShowToastError(result.error ?: "Error desconocido"))
                }

                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }

    private fun emitEffect(effect: PreviewUiEffect) = viewModelScope.launch(dispatcherProvider.mainImmediate) {
        _eventFlow.emit(effect)
    }

    private fun setupLoading(value: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = value)
    }

}