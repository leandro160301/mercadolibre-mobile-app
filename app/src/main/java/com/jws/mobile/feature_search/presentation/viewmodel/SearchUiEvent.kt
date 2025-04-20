package com.jws.mobile.feature_search.presentation.viewmodel


sealed class SearchUiEvent {
    data class RequestNavigateToPreview(val value: String) : SearchUiEvent()
    data object OnCancelClicked : SearchUiEvent()

}