package com.jws.jwsapi.feature_preview.domain

import com.jws.jwsapi.feature_preview.domain.items.ItemsResponse
import com.jws.jwsapi.feature_preview.domain.preview.Preview
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PreviewApi {
    @GET("users/{seller_id}/items/search")
    suspend fun getItemsBySeller(
        @Path("seller_id") sellerId: String,
        @Header("Authorization") authHeader: String,
        @Query("q") query: String? = null
    ): Response<ItemsResponse>

    @GET("items")
    suspend fun getItemsDetails(
        @Query("ids") ids: String,
        @Header("Authorization") authHeader: String
    ): Response<List<Preview>>
}