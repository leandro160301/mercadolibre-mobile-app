package com.jws.jwsapi.feature_preview.presentation.viewmodel

import com.jws.jwsapi.feature_preview.domain.preview.Preview

sealed class PreviewUiEvent {
    data class FetchItems(val query: String? = null) : PreviewUiEvent()
    object RequestNavigateToSearch : PreviewUiEvent()
    data class RequestNavigateToDetails(val preview: Preview) : PreviewUiEvent()
}