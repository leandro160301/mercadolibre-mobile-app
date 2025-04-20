package com.jws.mobile.feature_preview.presentation.viewmodel

import com.jws.mobile.feature_preview.domain.preview.Preview

data class PreviewUiState(
    val isLoading: Boolean = false,
    val query: String? = null,
    val deleteButtonIsVisible: Boolean = false,
    val previewList: List<Preview> = emptyList(),
    val selectedImageIndexMap: Map<String, Int> = emptyMap()
)

