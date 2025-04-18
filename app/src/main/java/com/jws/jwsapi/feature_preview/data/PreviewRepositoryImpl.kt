package com.jws.jwsapi.feature_preview.data

import com.jws.jwsapi.Constants.TOKEN
import com.jws.jwsapi.Constants.VENDOR_ID
import com.jws.jwsapi.feature_preview.domain.PreviewRepository
import com.jws.jwsapi.feature_preview.domain.items.ItemsApi
import com.jws.jwsapi.feature_preview.domain.items.ItemsResponse
import com.jws.jwsapi.shared.Resource
import javax.inject.Inject

class PreviewRepositoryImpl @Inject constructor(
    private val itemsApi: ItemsApi
): PreviewRepository {
    override suspend fun getItemsBySeller(): Resource<ItemsResponse> {
        return try {
            val response = itemsApi.getItemsBySeller(VENDOR_ID, "Bearer $TOKEN")
            if (response.isSuccessful) {
                Resource.Success(response.body())
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                Resource.Error(error = errorMsg)
            }
        } catch (e: Exception) {
            Resource.Error(error = e.localizedMessage ?: "Exception occurred")
        }
    }

}