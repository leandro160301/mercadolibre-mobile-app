package com.jws.mobile.feature_preview.domain.preview

import com.google.gson.annotations.SerializedName
import com.jws.mobile.core.features.Picture

data class Body(
    @SerializedName("available_quantity") val availableQuantity: Int,
    @SerializedName("base_price") val basePrice: Int,
    @SerializedName("condition") val condition: String,
    @SerializedName("pictures") val pictures: List<Picture>,
    @SerializedName("price") val price: Int,
    @SerializedName("title") val title: String,
    @SerializedName("warranty") val warranty: String,
    @SerializedName("id") val id: String
)