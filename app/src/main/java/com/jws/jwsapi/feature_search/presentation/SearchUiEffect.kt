package com.jws.jwsapi.feature_search.presentation

sealed class SearchUiEffect {
    data class ShowToastError(val error: String) : SearchUiEffect()
    data class ShowToastMessage(val message: String) : SearchUiEffect()
    data class NavigateToPreview(val value: String) : SearchUiEffect()
}