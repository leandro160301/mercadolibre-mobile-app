package com.jws.mobile.feature_preview.data

import com.jws.mobile.core.network.NetworkResultWrapper
import com.jws.mobile.core.utils.Constants.TOKEN
import com.jws.mobile.core.utils.Constants.VENDOR_ID
import com.jws.mobile.core.utils.Resource
import com.jws.mobile.feature_preview.domain.PreviewApi
import com.jws.mobile.feature_preview.domain.PreviewRepository
import com.jws.mobile.feature_preview.domain.items.ItemsResponse
import com.jws.mobile.feature_preview.domain.preview.Preview
import javax.inject.Inject

class PreviewRepositoryImpl @Inject constructor(
    private val previewApi: PreviewApi
) : PreviewRepository {

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