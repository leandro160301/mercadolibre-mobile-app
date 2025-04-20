package com.jws.mobile.feature_preview.presentation.viewmodel

sealed class PreviewUiEvent {
    data class FetchItems(val query: String? = null) : PreviewUiEvent()
    data object RequestNavigateToSearch : PreviewUiEvent()
    data class RequestNavigateToDetails(val productId: String) : PreviewUiEvent()
    data object OnDeleteSearchClicked : PreviewUiEvent()
}