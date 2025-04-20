package com.jws.mobile.feature_detail.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jws.mobile.core.di.DispatcherProvider
import com.jws.mobile.core.utils.Resource
import com.jws.mobile.feature_detail.domain.FetchDetailByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val fetchDetailByIdUseCase: FetchDetailByIdUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    private val _eventFlow = MutableSharedFlow<DetailUiEffect>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var hasFetched = false

    fun onEvent(event: DetailUiEvent) {
        when (event) {
            is DetailUiEvent.RequestNavigateToSearch -> navSearch()
            is DetailUiEvent.FetchDetails -> loadItems(event.id)
            is DetailUiEvent.OnBackClicked -> backClick()
        }
    }

    private fun backClick() = emitEffect(DetailUiEffect.OnBackClicked)

    private fun navSearch() = emitEffect(DetailUiEffect.NavigateToSearch)

    private fun loadItems(id: String) {
        if (hasFetched) return

        viewModelScope.launch {
            hasFetched = true
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = fetchDetailByIdUseCase(id)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, detail = result.data)
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    emitEffect(DetailUiEffect.ShowToastError(result.error ?: "Error desconocido"))
                }
                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }

    private fun emitEffect(effect: DetailUiEffect) {
        viewModelScope.launch(dispatcherProvider.mainImmediate) {
            _eventFlow.emit(effect)
        }
    }
}