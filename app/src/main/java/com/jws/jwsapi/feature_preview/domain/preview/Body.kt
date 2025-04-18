package com.jws.jwsapi.feature_preview.domain.preview

data class Body(
    val accepts_mercadopago: Boolean,
    val available_quantity: Int,
    val base_price: Int,
    val condition: String,
    val permalink: String,
    val pictures: List<Picture>,
    val price: Int,
    val title: String,
    val warranty: String
)