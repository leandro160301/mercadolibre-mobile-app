package com.jws.jwsapi.feature_preview.presentation.epoxy

import com.jws.jwsapi.R
import com.jws.jwsapi.databinding.ItemFileBinding
import com.jws.jwsapi.feature_preview.domain.preview.Preview
import com.jws.jwsapi.shared.ViewBindingKotlinModel

data class ListPreviewModel(
    private val preview: Preview,
    private val onPreviewSelected: (preview: Preview) -> Unit,
) : ViewBindingKotlinModel<ItemFileBinding>(R.layout.item_file) {

    override fun ItemFileBinding.bind() {
        productTitle.text = preview.body.title
        productPrice.text = preview.body.price.toString()
        productCondition.text = preview.body.condition
        productAvailability.text = preview.body.availableQuantity.toString()
        productWarranty.text = preview.body.warranty
        card.setOnClickListener { onPreviewSelected(preview) }
    }


}