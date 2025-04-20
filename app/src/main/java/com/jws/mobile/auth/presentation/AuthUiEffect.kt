package com.jws.mobile.auth.presentation

sealed class AuthUiEffect {
    data class ShowToastError(val error: String) : AuthUiEffect()
    data object NavigateToMain : AuthUiEffect()
}