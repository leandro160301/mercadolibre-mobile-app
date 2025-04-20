package com.jws.mobile.feature_detail.presentation.viewmodel

sealed class DetailUiEvent {
    data class FetchDetails(val id: String) : DetailUiEvent()
    data object RequestNavigateToSearch : DetailUiEvent()
    data object OnBackClicked : DetailUiEvent()
}