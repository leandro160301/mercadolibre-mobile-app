package com.jws.mobile.auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jws.mobile.auth.domain.Auth
import com.jws.mobile.auth.domain.LoginUseCase
import com.jws.mobile.auth.domain.UpdateRefreshTokenUseCase
import com.jws.mobile.core.di.DispatcherProvider
import com.jws.mobile.core.features.TokenManager
import com.jws.mobile.core.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val updateRefreshTokenUseCase: UpdateRefreshTokenUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    private val _eventFlow = MutableSharedFlow<AuthUiEffect>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        login()
    }

    private fun navToMain() = emitEffect(AuthUiEffect.NavigateToMain)

    private fun login() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isLoading = true)
        try {
            when (val result = loginUseCase()) {
                is Resource.Success -> handleSuccess(result)
                is Resource.Error -> handleAuthError(result.error ?: "Error desconocido")
                is Resource.Loading -> _uiState.value = _uiState.value.copy(isLoading = true)
            }
        } catch (e: Exception) {
            handleAuthError(e.message)
        }
    }

    private suspend fun handleSuccess(result: Resource<Auth>) {
        _uiState.value = _uiState.value.copy(isLoading = false)
        result.data?.let {
            TokenManager.setToken(it.accessToken)
            try {
                updateRefreshTokenUseCase(it.refreshToken)
            } catch (e: Exception) {
                handleAuthError(e.message)
            }
        }
        navToMain()
    }

    private fun handleAuthError(message: String?) {
        _uiState.value = _uiState.value.copy(isLoading = false)
        emitEffect(AuthUiEffect.ShowToastError(message ?: "Error desconocido"))
    }

    private fun emitEffect(effect: AuthUiEffect) {
        viewModelScope.launch(dispatcherProvider.mainImmediate) {
            _eventFlow.emit(effect)
        }
    }
}