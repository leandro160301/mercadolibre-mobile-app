package com.jws.jwsapi.feature_detail.presentation

import com.jws.jwsapi.feature_detail.domain.Detail

data class DetailUiState (
    val isLoading: Boolean = false,
    val previewList: Detail? = null
)

