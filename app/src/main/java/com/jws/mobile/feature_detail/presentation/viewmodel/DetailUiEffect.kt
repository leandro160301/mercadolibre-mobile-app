package com.jws.mobile.feature_detail.presentation.viewmodel

sealed class DetailUiEffect {
    data class ShowToastError(val error: String) : DetailUiEffect()
    data class ShowToastMessage(val message: String) : DetailUiEffect()
    object NavigateToSearch : DetailUiEffect()
    object OnBackClicked : DetailUiEffect()
}