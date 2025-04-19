package com.jws.jwsapi.feature_search.presentation

import com.jws.jwsapi.feature_search.domain.Search

data class SearchUiState (
    val search: List<Search> = emptyList()
)

