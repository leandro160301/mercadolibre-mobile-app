package com.jws.jwsapi.feature_preview.presentation

import com.jws.jwsapi.feature_preview.domain.preview.Preview

data class PreviewUiState (
    val isLoading: Boolean = false,
    val previewList: List<Preview> = emptyList()
)

