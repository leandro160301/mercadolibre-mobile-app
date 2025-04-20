package com.jws.mobile.feature_search.presentation.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.jws.mobile.feature_search.presentation.viewmodel.SearchUiState

class SearchEpoxyController(
    private val onSearchSelected: (value: String) -> Unit
) : TypedEpoxyController<SearchUiState>() {
    override fun buildModels(state: SearchUiState?) {
        val uiState = state ?: return

        /*if (uiState.isLoading) {
            LoadingPreviewModel().id("loading")
                .addTo(this)
            return
        }*/

        uiState.search.forEachIndexed { index, search ->
            ListSearchModel(
                search = search,
                onSearchSelected = onSearchSelected
            ).id(index)
                .addTo(this)
        }

    }

}