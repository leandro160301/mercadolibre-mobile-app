package com.jws.jwsapi.feature_preview.presentation.viewmodel


sealed class PreviewUiEffect {
    data class ShowToastError(val error: String) : PreviewUiEffect()
    data class ShowToastMessage(val message: String) : PreviewUiEffect()
    object NavigateToSearch : PreviewUiEffect()
    object OnDeleteSearchClicked: PreviewUiEffect()
    data class NavigateToDetails(val productId: String) : PreviewUiEffect()
}