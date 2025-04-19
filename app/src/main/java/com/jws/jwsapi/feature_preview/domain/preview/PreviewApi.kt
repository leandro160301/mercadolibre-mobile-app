package com.jws.jwsapi.feature_preview.domain.preview

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface PreviewApi {
    @GET("items")
    suspend fun getItemsDetails(
        @Query("ids") ids: String,
        @Header("Authorization") authHeader: String
    ): Response<List<Preview>>
}