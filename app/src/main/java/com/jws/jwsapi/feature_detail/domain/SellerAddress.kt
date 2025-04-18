package com.jws.jwsapi.feature_detail.domain

data class SellerAddress(
    val address_line: String,
    val city: City,
    val comment: String,
    val country: Country,
    val id: Int,
    val latitude: Double,
    val longitude: Double,
    val state: State,
    val zip_code: String
)