package com.jws.mobile.feature_preview.domain.items

import com.google.gson.annotations.SerializedName


data class ItemsResponse(
    @SerializedName("seller_id") val sellerId: String,
    @SerializedName("results") val results: List<String>
)