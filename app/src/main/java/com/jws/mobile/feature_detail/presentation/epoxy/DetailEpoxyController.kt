package com.jws.mobile.feature_detail.presentation.epoxy

import com.airbnb.epoxy.TypedEpoxyController
import com.jws.mobile.core.ui.epoxy.LoadingModel
import com.jws.mobile.feature_detail.presentation.viewmodel.DetailUiState

class DetailEpoxyController : TypedEpoxyController<DetailUiState>() {
    override fun buildModels(state: DetailUiState?) {
        val uiState = state ?: return

        if (uiState.isLoading) {
            LoadingModel().id("loading")
                .spanSizeOverride { _, _, _ -> 2 }
                .addTo(this)
            return
        }

        uiState.detail?.let { detail ->
            ListDetailModel(detail = detail)
                .id("detail_preview")
                .addTo(this)
        }

    }

}