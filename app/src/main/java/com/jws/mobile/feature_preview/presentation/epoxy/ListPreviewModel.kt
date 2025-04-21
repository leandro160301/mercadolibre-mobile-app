package com.jws.mobile.feature_preview.presentation.epoxy

import android.annotation.SuppressLint
import androidx.viewpager2.widget.ViewPager2
import com.jws.mobile.R
import com.jws.mobile.core.helpers.ViewBindingKotlinModel
import com.jws.mobile.core.utils.ImagePagerAdapter
import com.jws.mobile.databinding.ItemPreviewBinding
import com.jws.mobile.feature_preview.domain.preview.Preview

data class ListPreviewModel(
    private val preview: Preview,
    private val selectedPosition: Int,
    private val onPreviewSelected: (productId: String) -> Unit,
    private val onImagePageChanged: (productId: String, position: Int) -> Unit

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

        (imagePager.getTag(R.id.view_pager_callback_tag) as? ViewPager2.OnPageChangeCallback)?.let {
            imagePager.unregisterOnPageChangeCallback(it)
        }

        imagePager.adapter = ImagePagerAdapter(preview.body.pictures)
        imageIndicator.setViewPager(imagePager)

        imagePager.setCurrentItem(selectedPosition, false)

        val callback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                onImagePageChanged(preview.body.id, position)
            }
        }

        imagePager.registerOnPageChangeCallback(callback)
        imagePager.setTag(R.id.view_pager_callback_tag, callback)

        card.setOnClickListener { onPreviewSelected(preview.body.id) }
    }

    override fun ItemPreviewBinding.unbind() {
        (imagePager.getTag(R.id.view_pager_callback_tag) as? ViewPager2.OnPageChangeCallback)?.let {
            imagePager.unregisterOnPageChangeCallback(it)
        }
        imagePager.setTag(R.id.view_pager_callback_tag, null)
    }
}