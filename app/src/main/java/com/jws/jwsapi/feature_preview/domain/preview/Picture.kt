package com.jws.jwsapi.feature_preview.domain.preview

data class Picture(
    val id: String,
    val max_size: String,
    val quality: String,
    val secure_url: String,
    val size: String,
    val url: String
)