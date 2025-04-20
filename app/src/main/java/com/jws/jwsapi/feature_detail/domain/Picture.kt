package com.jws.jwsapi.feature_detail.domain

import com.google.gson.annotations.SerializedName

data class Picture(
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String
)