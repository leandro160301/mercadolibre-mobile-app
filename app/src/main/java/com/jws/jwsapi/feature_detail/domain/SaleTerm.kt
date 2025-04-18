package com.jws.jwsapi.feature_detail.domain

data class SaleTerm(
    val id: String,
    val name: String,
    val value_id: String,
    val value_name: String,
    val value_struct: ValueStruct,
    val value_type: String,
    val values: List<Value>
)