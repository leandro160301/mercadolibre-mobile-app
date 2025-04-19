package com.jws.jwsapi.feature_preview.data

import com.jws.jwsapi.Constants.TOKEN
import com.jws.jwsapi.Constants.VENDOR_ID
import com.jws.jwsapi.feature_preview.domain.PreviewRepository
import com.jws.jwsapi.feature_preview.domain.items.ItemsApi
import com.jws.jwsapi.feature_preview.domain.items.ItemsResponse
import com.jws.jwsapi.feature_preview.domain.preview.Preview
import com.jws.jwsapi.feature_preview.domain.preview.PreviewApi
import com.jws.jwsapi.shared.Resource
import retrofit2.Response
import javax.inject.Inject

class PreviewRepositoryImpl @Inject constructor(
    private val itemsApi: ItemsApi,
    private val previewApi: PreviewApi
): PreviewRepository {

    override suspend fun getItemsBySeller(query: String?): Resource<ItemsResponse> {
        return getApiResponse { itemsApi.getItemsBySeller(VENDOR_ID, "Bearer $TOKEN", query = query) }
    }

    override suspend fun getItemsDetails(ids: String): Resource<List<Preview>> {
        return getApiResponse { previewApi.getItemsDetails(ids, "Bearer $TOKEN") }
    }

    private suspend fun <T> getApiResponse(
        apiCall: suspend () -> Response<T>
    ): Resource<T> {
        return try {
            val response = apiCall()
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