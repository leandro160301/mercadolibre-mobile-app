package com.jws.mobile.feature_search.presentation.viewmodel

import com.jws.mobile.feature_search.domain.Search

data class SearchUiState(
    val search: List<Search> = emptyList()
)

