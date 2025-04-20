package com.jws.mobile.feature_detail.data

import com.jws.mobile.core.features.TokenManager
import com.jws.mobile.core.network.NetworkResultWrapper
import com.jws.mobile.core.utils.Resource
import com.jws.mobile.feature_detail.domain.Detail
import com.jws.mobile.feature_detail.domain.DetailApi
import com.jws.mobile.feature_detail.domain.DetailRepository
import javax.inject.Inject

class DetailRepositoryImpl @Inject constructor(
    private val detailApi: DetailApi
) : DetailRepository {

    override suspend fun getItemsDetails(id: String): Resource<Detail> {
        return NetworkResultWrapper.getApiResponse {
            detailApi.getItemsDetails(id, "Bearer ${TokenManager.getToken()}")
        }
    }
}