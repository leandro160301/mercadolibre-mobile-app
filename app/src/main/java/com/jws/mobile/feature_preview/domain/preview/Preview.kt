package com.jws.mobile.feature_preview.domain.preview

import com.google.gson.annotations.SerializedName

data class Preview(
    @SerializedName("body") val body: Body,
    @SerializedName("code") val code: Int
)