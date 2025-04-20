package com.jws.mobile.feature_preview.presentation.epoxy

import android.annotation.SuppressLint
import com.jws.mobile.R
import com.jws.mobile.core.helpers.ViewBindingKotlinModel
import com.jws.mobile.core.utils.ImagePagerAdapter
import com.jws.mobile.databinding.ItemPreviewBinding
import com.jws.mobile.feature_preview.domain.preview.Preview

data class ListPreviewModel(
    private val preview: Preview,
    private val onPreviewSelected: (productId: String) -> Unit,
) : ViewBindingKotlinModel<ItemPreviewBinding>(R.layout.item_preview) {

    @SuppressLint("SetTextI18n")
    override fun ItemPreviewBinding.bind() {
        productTitle.text = preview.body.title
        productPrice.text = "$${preview.body.price}"
        val conditionFormatted = when (preview.body.condition) {
            "new" -> "Nuevo"
            "used" -> "Usado"
            else -> "Condición desconocida"
        }
        productCondition.text = "Condición: $conditionFormatted"
        productAvailability.text = "Disponible: ${preview.body.availableQuantity}"
        productWarranty.text = "Garantía: ${preview.body.warranty}"

        val adapter = ImagePagerAdapter(preview.body.pictures)
        imagePager.adapter = adapter
        imageIndicator.setViewPager(imagePager)

        card.setOnClickListener { onPreviewSelected(preview.body.id) }
    }


}