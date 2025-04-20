package com.jws.jwsapi.feature_detail.domain

import com.jws.jwsapi.shared.Resource

interface DetailRepository {
    suspend fun getItemsDetails(id: String): Resource<Detail>
}