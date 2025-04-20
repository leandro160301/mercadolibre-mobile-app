package com.jws.mobile.feature_preview.presentation.epoxy

import android.view.View
import com.jws.mobile.R
import com.jws.mobile.core.helpers.ViewBindingKotlinModel
import com.jws.mobile.databinding.ItemLoadingBinding

class LoadingPreviewModel : ViewBindingKotlinModel<ItemLoadingBinding>(R.layout.item_loading) {

    override fun ItemLoadingBinding.bind() {
        progressBar.visibility = View.VISIBLE
    }
}