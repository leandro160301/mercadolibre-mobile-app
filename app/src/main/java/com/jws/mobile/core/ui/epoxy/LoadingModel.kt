package com.jws.mobile.core.ui.epoxy

import android.view.View
import com.jws.mobile.R
import com.jws.mobile.core.helpers.ViewBindingKotlinModel
import com.jws.mobile.databinding.ItemLoadingBinding

class LoadingModel : ViewBindingKotlinModel<ItemLoadingBinding>(R.layout.item_loading) {

    override fun ItemLoadingBinding.bind() {
        progressBar.visibility = View.VISIBLE
    }
}