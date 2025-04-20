package com.jws.mobile.feature_detail.presentation.epoxy

import android.annotation.SuppressLint
import com.jws.mobile.core.utils.ImagePagerAdapter
import com.jws.mobile.databinding.ItemDetailBinding
import com.jws.mobile.feature_detail.domain.Detail

class DetailUiBinder(private val binding: ItemDetailBinding) {

    @SuppressLint("SetTextI18n")
    fun bind(detail: Detail) {
        binding.productTitle.text = detail.title
        binding.productPrice.text = "$${detail.price}"

        detail.pictures.let { pictures ->
            val adapter = ImagePagerAdapter(pictures)
            binding.imagePager.adapter = adapter
            binding.imageIndicator.setViewPager(binding.imagePager)
        }

        val conditionFormatted = when (detail.condition) {
            "new" -> "Nuevo"
            "used" -> "Usado"
            else -> "Condición desconocida"
        }

        binding.productWarranty.text = detail.warranty
        binding.productCondition.text =
            "$conditionFormatted | ${detail.availableQuantity} disponibles"
        binding.shippingInfo.text = "Envío gratis a todo el país"
        binding.productDescription.text = "Este producto es ideal para..."
        binding.productSpecs.text = buildString {
            append("• Marca: X\n")
            append("• Modelo: Y\n")
            append("• Color: Negro\n")
            append("• Peso: 1.5kg")
        }

    }
}