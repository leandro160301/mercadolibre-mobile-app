package com.jws.jwsapi.feature_preview.presentation.epoxy

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.jws.jwsapi.R
import com.jws.jwsapi.databinding.ItemPreviewBinding
import com.jws.jwsapi.feature_preview.domain.preview.Preview
import com.jws.jwsapi.shared.ViewBindingKotlinModel
import com.jws.jwsapi.utils.ImagePagerAdapter

data class ListPreviewModel(
    private val preview: Preview,
    private val onPreviewSelected: (preview: Preview) -> Unit,
) : ViewBindingKotlinModel<ItemPreviewBinding>(R.layout.item_preview) {

    @SuppressLint("SetTextI18n")
    override fun ItemPreviewBinding.bind() {
        productTitle.text = preview.body.title
        productPrice.text = "$${preview.body.price}"
        productCondition.text = "Condición: ${preview.body.condition.capitalize()}"
        productAvailability.text = "Disponible: ${preview.body.availableQuantity}"
        productWarranty.text = "Garantía: ${preview.body.warranty}"

        val adapter = ImagePagerAdapter(preview.body.pictures)
        imagePager.adapter = adapter
        imageIndicator.setViewPager(imagePager)

        card.setOnClickListener { onPreviewSelected(preview) }
    }


}