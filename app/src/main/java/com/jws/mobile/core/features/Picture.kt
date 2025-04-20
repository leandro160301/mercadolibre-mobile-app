package com.jws.mobile.core.features

import com.google.gson.annotations.SerializedName

data class Picture(
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String
)