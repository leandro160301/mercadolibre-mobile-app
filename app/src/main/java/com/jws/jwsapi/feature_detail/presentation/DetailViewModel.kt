package com.jws.jwsapi.feature_detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jws.jwsapi.feature_detail.domain.FetchDetailByIdUseCase
import com.jws.jwsapi.feature_search.presentation.SearchUiEffect
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
class DetailViewModel @Inject constructor(
    private val fetchDetailByIdUseCase: FetchDetailByIdUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState

    private val _eventFlow = MutableSharedFlow<DetailUiEffect>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: DetailUiEvent) {
        when (event) {
            is DetailUiEvent.RequestNavigateToSearch -> navSearch()
            is DetailUiEvent.RequestNavigateToDetails -> navDetails()
            is DetailUiEvent.FetchDetails -> loadItems(event.id)
            DetailUiEvent.OnBackClicked -> backClick()
        }
    }

    private fun backClick() = emitEffect(DetailUiEffect.OnBackClicked)

    private fun navSearch() {
        emitEffect(DetailUiEffect.NavigateToSearch)
    }

    private fun navDetails() {
//        findNavController().navigate(R.id.action_previewFragment_to_searchFragment)
    }

    private fun loadItems(id: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = fetchDetailByIdUseCase(id)) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false, previewList = result.data)
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


    private fun emitEffect(effect: DetailUiEffect) = viewModelScope.launch(dispatcherProvider.mainImmediate) {
        _eventFlow.emit(effect)
    }

}