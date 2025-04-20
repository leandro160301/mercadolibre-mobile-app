package com.jws.mobile.feature_detail.presentation.epoxy

import com.jws.mobile.R
import com.jws.mobile.core.helpers.ViewBindingKotlinModel
import com.jws.mobile.databinding.ItemDetailBinding
import com.jws.mobile.feature_detail.domain.Detail

data class ListDetailModel(
    private val detail: Detail,
) : ViewBindingKotlinModel<ItemDetailBinding>(R.layout.item_detail) {

    override fun ItemDetailBinding.bind() {
        DetailUiBinder(this).bind(detail)
    }

}