package com.jws.jwsapi.feature_preview.presentation


sealed class PreviewUiEvent {
    object FetchItems : PreviewUiEvent()
    data class UpdateSystem(val file: String) : PreviewUiEvent()
}