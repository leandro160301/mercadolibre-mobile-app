package com.jws.mobile.feature_detail.presentation.epoxy

import android.annotation.SuppressLint
import androidx.viewpager2.widget.ViewPager2
import com.jws.mobile.core.utils.ImagePagerAdapter
import com.jws.mobile.databinding.ItemDetailBinding
import com.jws.mobile.feature_detail.presentation.viewmodel.DetailUiState

class DetailUiBinder(
    private val binding: ItemDetailBinding,
    private val onImagePageChanged: (position: Int) -> Unit
) {

    @SuppressLint("SetTextI18n")
    fun bind(detailUiState: DetailUiState) {
        val detail = detailUiState.detail
        detail?.let {
            binding.productTitle.text = it.title
            binding.productPrice.text = "$${it.price}"

            it.pictures.let { pictures ->
                val adapter = ImagePagerAdapter(pictures)
                binding.imagePager.adapter = adapter
                binding.imageIndicator.setViewPager(binding.imagePager)
                binding.imagePager.setCurrentItem(detailUiState.currentImagePosition, false)

                binding.imagePager.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        onImagePageChanged(position)
                    }
                })
            }

            val conditionFormatted = when (it.condition) {
                "new" -> "Nuevo"
                "used" -> "Usado"
                else -> "Condición desconocida"
            }

            binding.productWarranty.text = it.warranty
            binding.productCondition.text =
                "$conditionFormatted | ${it.availableQuantity} disponibles"
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
}