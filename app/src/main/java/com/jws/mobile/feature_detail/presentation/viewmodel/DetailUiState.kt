package com.jws.mobile.feature_detail.presentation.viewmodel

import com.jws.mobile.feature_detail.domain.Detail

data class DetailUiState(
    val isLoading: Boolean = false,
    val detail: Detail? = null
)

