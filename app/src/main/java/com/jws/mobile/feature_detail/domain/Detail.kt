package com.jws.mobile.feature_detail.domain

import com.google.gson.annotations.SerializedName
import com.jws.mobile.core.features.Picture

data class Detail(
    @SerializedName("available_quantity") val availableQuantity: Int,
    @SerializedName("base_price") val basePrice: Int,
    @SerializedName("condition") val condition: String,
    @SerializedName("pictures") val pictures: List<Picture>,
    @SerializedName("price") val price: Int,
    @SerializedName("title") val title: String,
    @SerializedName("warranty") val warranty: String,
    @SerializedName("accepts_mercadopago") val acceptsMercadopago: Boolean,
    @SerializedName("buying_mode") val buyingMode: String,
    @SerializedName("id") val id: String,
    @SerializedName("initial_quantity") val initialQuantity: Int,
    @SerializedName("international_delivery_mode") val internationalDeliveryMode: String,
)