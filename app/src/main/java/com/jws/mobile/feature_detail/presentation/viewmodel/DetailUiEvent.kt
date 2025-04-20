package com.jws.mobile.feature_detail.presentation.viewmodel

import com.jws.mobile.feature_preview.domain.preview.Preview

sealed class DetailUiEvent {
    data class FetchDetails(val id: String) : DetailUiEvent()
    object RequestNavigateToSearch : DetailUiEvent()
    object OnBackClicked : DetailUiEvent()
    data class RequestNavigateToDetails(val preview: Preview) : DetailUiEvent()
}