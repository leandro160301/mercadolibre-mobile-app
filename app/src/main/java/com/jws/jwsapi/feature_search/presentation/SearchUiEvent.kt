package com.jws.jwsapi.feature_search.presentation


sealed class SearchUiEvent {
    data class RequestNavigateToPreview(val value: String) : SearchUiEvent()
}