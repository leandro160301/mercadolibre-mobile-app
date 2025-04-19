package com.jws.jwsapi.feature_preview.domain.preview

import com.google.gson.annotations.SerializedName

data class Body(
    @SerializedName("accepts_mercadopago") val acceptsMercadopago: Boolean,
    @SerializedName("available_quantity") val availableQuantity: Int,
    @SerializedName("base_price") val basePrice: Int,
    @SerializedName("condition") val condition: String,
    @SerializedName("pictures") val pictures: List<Picture>,
    @SerializedName("price") val price: Int,
    @SerializedName("title") val title: String,
    @SerializedName("warranty") val warranty: String
)