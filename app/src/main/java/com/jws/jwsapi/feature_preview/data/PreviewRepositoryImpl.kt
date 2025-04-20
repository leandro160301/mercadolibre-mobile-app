package com.jws.jwsapi.feature_preview.data

import com.jws.jwsapi.Constants.TOKEN
import com.jws.jwsapi.Constants.VENDOR_ID
import com.jws.jwsapi.feature_preview.domain.PreviewApi
import com.jws.jwsapi.feature_preview.domain.PreviewRepository
import com.jws.jwsapi.feature_preview.domain.items.ItemsResponse
import com.jws.jwsapi.feature_preview.domain.preview.Preview
import com.jws.jwsapi.shared.NetworkResultWrapper
import com.jws.jwsapi.shared.Resource
import retrofit2.Response
import javax.inject.Inject

class PreviewRepositoryImpl @Inject constructor(
    private val previewApi: PreviewApi
): PreviewRepository {

    override suspend fun getItemsBySeller(query: String?): Resource<ItemsResponse> {
        return NetworkResultWrapper.getApiResponse {
            previewApi.getItemsBySeller(VENDOR_ID, "Bearer $TOKEN", query = query)
        }
    }

    override suspend fun getItemsDetails(ids: String): Resource<List<Preview>> {
        return NetworkResultWrapper.getApiResponse {
            previewApi.getItemsDetails(ids, "Bearer $TOKEN")
        }
    }



}