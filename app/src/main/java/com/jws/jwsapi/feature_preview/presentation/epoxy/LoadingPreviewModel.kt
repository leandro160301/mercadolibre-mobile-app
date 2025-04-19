package com.jws.jwsapi.feature_preview.presentation.epoxy

import android.view.View
import com.jws.jwsapi.R
import com.jws.jwsapi.databinding.ItemLoadingBinding
import com.jws.jwsapi.shared.ViewBindingKotlinModel

class LoadingPreviewModel : ViewBindingKotlinModel<ItemLoadingBinding>(R.layout.item_loading) {

    override fun ItemLoadingBinding.bind() {
        progressBar.visibility = View.VISIBLE
    }
}