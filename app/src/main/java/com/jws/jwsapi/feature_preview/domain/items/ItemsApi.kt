package com.jws.jwsapi.feature_preview.domain.items

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ItemsApi {
    @GET("users/{seller_id}/items/search")
    suspend fun getItemsBySeller(
        @Path("seller_id") sellerId: String,
        @Header("Authorization") authHeader: String,
        @Query("q") query: String? = null
    ): Response<ItemsResponse>
}