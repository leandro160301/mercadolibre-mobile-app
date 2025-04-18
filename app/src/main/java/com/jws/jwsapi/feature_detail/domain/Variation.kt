package com.jws.jwsapi.feature_detail.domain

data class Variation(
    val attribute_combinations: List<AttributeCombination>,
    val available_quantity: Int,
    val catalog_product_id: String,
    val id: Long,
    val inventory_id: Any,
    val item_relations: List<Any?>,
    val picture_ids: List<String>,
    val price: Int,
    val sale_terms: List<Any?>,
    val seller_custom_field: Any,
    val sold_quantity: Int,
    val user_product_id: String
)