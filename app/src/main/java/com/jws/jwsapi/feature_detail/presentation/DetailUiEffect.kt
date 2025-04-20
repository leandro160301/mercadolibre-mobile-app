package com.jws.jwsapi.feature_detail.presentation

import com.jws.jwsapi.feature_search.presentation.SearchUiEffect

sealed class DetailUiEffect {
    data class ShowToastError(val error: String) : DetailUiEffect()
    data class ShowToastMessage(val message: String) : DetailUiEffect()
    object NavigateToSearch : DetailUiEffect()
    object OnBackClicked : DetailUiEffect()
}