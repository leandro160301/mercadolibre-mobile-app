package com.jws.jwsapi.feature_preview.presentation

sealed class PreviewUiEffect {
    data class ShowToastError(val error: String) : PreviewUiEffect()
    data class ShowToastMessage(val message: String) : PreviewUiEffect()
}