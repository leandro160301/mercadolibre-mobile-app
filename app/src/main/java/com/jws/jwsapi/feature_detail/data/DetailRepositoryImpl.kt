package com.jws.jwsapi.feature_detail.data

import com.jws.jwsapi.Constants.TOKEN
import com.jws.jwsapi.Constants.VENDOR_ID
import com.jws.jwsapi.feature_detail.domain.Detail
import com.jws.jwsapi.feature_detail.domain.DetailApi
import com.jws.jwsapi.feature_detail.domain.DetailRepository
import com.jws.jwsapi.shared.NetworkResultWrapper
import com.jws.jwsapi.shared.Resource
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailApi: DetailApi
): DetailRepository {

    override suspend fun getItemsDetails(id: String): Resource<Detail> {
        return NetworkResultWrapper.getApiResponse {
            detailApi.getItemsDetails(id, "Bearer $TOKEN")
        }
    }
}