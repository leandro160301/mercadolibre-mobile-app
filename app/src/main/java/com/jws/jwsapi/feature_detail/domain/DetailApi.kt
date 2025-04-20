package com.jws.jwsapi.feature_detail.domain

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface DetailApi {
    @GET("items/{id}")
    suspend fun getItemsDetails(
        @Path("id") sellerId: String,
        @Header("Authorization") authHeader: String
    ): Response<Detail>
}