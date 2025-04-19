package com.jws.jwsapi.feature_preview.presentation.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.jws.jwsapi.feature_preview.domain.preview.Preview
import com.jws.jwsapi.feature_preview.presentation.PreviewUiState

class PreviewEpoxyController(
    private val onPreviewSelected: (preview: Preview) -> Unit
) : TypedEpoxyController<PreviewUiState>() {
    override fun buildModels(state: PreviewUiState?) {
        val uiState = state ?: return

        if (uiState.isLoading) {
            LoadingPreviewModel().id("loading")
                .spanSizeOverride { _, _, _ -> 2 }
                .addTo(this)
            return
        }

        uiState.previewList.forEachIndexed { index, preview ->
            ListPreviewModel(
                preview = preview,
                onPreviewSelected = onPreviewSelected
            ).id(index)
                .spanSizeOverride { _, _, _ -> 1 }
                .addTo(this)
        }

    }

}