package com.jws.jwsapi.feature_detail.presentation

import com.jws.jwsapi.feature_preview.domain.preview.Preview

sealed class DetailUiEvent {
    data class FetchDetails(val id: String) : DetailUiEvent()
    object RequestNavigateToSearch : DetailUiEvent()
    object OnBackClicked : DetailUiEvent()
    data class RequestNavigateToDetails(val preview: Preview) : DetailUiEvent()
}