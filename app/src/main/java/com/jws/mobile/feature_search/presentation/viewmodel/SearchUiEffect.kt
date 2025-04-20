package com.jws.mobile.feature_search.presentation.viewmodel

sealed class SearchUiEffect {
    data class ShowToastError(val error: String) : SearchUiEffect()
    data class ShowToastMessage(val message: String) : SearchUiEffect()
    data class NavigateToPreview(val value: String) : SearchUiEffect()
    data object OnCancelClicked : SearchUiEffect()

}