package com.jws.jwsapi.feature_preview.presentation

data class PreviewUiState (
    val isLoading: Boolean = false,
    val title: String = "",
    val brand: String = "",
    val price: String = "",
    val condition: String = ""
)

