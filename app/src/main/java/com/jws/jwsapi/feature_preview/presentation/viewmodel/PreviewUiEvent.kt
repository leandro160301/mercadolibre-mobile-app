package com.jws.jwsapi.feature_preview.presentation.viewmodel

sealed class PreviewUiEvent {
    data class FetchItems(val query: String? = null) : PreviewUiEvent()
    object RequestNavigateToSearch : PreviewUiEvent()
    data class RequestNavigateToDetails(val productId: String) : PreviewUiEvent()
    object OnDeleteSearchClicked: PreviewUiEvent()
}