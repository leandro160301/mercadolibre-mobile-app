package com.jws.mobile.feature_preview.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jws.mobile.core.di.DispatcherProvider
import com.jws.mobile.core.utils.Resource
import com.jws.mobile.feature_preview.domain.FetchItemsBySellerUseCase
import com.jws.mobile.feature_preview.domain.FetchPreviewsByIdUseCase
import com.jws.mobile.feature_preview.domain.items.ItemsResponse
import com.jws.mobile.feature_preview.domain.preview.Preview
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

    fun onEvent(event: PreviewUiEvent) {
        when (event) {
            is PreviewUiEvent.RequestNavigateToSearch -> navSearch()
            is PreviewUiEvent.RequestNavigateToDetails -> navDetails(event.productId)
            is PreviewUiEvent.FetchItems -> loadItems(event.query)
            is PreviewUiEvent.OnDeleteSearchClicked -> deleteSearch()
            is PreviewUiEvent.OnImagePageChanged -> onImagePageChanged(
                event.productId,
                event.position
            )
        }
    }

    private fun loadItems(query: String?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                query = query,
                deleteButtonIsVisible = !query.isNullOrEmpty()
            )
            when (val result = fetchItemsBySellerUseCase(query)) {
                is Resource.Success -> handleItemsFetched(result)
                is Resource.Error -> showError(result)
                is Resource.Loading -> {}
            }
        }
    }

    private fun onImagePageChanged(productId: String, position: Int) {
        val updatedMap = uiState.value.pagerPositions.toMutableMap()
        updatedMap[productId] = position
        _uiState.value = uiState.value.copy(pagerPositions = updatedMap)
    }

    private fun loadPreviews(items: List<String>) {
        viewModelScope.launch {
            updateLoadingState(isLoading = true)
            when (val result = fetchPreviewsByIdUseCase(items)) {
                is Resource.Success -> handlePreviewsFetched(result)
                is Resource.Error -> showError(result)
                is Resource.Loading -> {}
            }
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = isLoading)
    }

    private fun handleItemsFetched(result: Resource<ItemsResponse>) {
        updateLoadingState(isLoading = false)
        val items = result.data?.results.orEmpty()
        loadPreviews(items)
    }

    private fun handlePreviewsFetched(result: Resource<List<Preview>>) {
        updateLoadingState(isLoading = false)
        result.data?.let { items -> _uiState.value = _uiState.value.copy(previewList = items) }
    }

    private fun <T> showError(result: Resource<T>) {
        updateLoadingState(isLoading = false)
        emitEffect(PreviewUiEffect.ShowToastError(result.error ?: "Error desconocido"))
    }

    private fun navSearch() = emitEffect(PreviewUiEffect.NavigateToSearch)

    private fun deleteSearch() = emitEffect(PreviewUiEffect.OnDeleteSearchClicked)

    private fun navDetails(id: String) = emitEffect(PreviewUiEffect.NavigateToDetails(id))

    private fun emitEffect(effect: PreviewUiEffect) {
        viewModelScope.launch(dispatcherProvider.mainImmediate) {
            _eventFlow.emit(effect)
        }
    }

}