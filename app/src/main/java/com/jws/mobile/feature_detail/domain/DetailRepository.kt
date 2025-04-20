package com.jws.mobile.feature_detail.domain

import com.jws.mobile.core.utils.Resource

interface DetailRepository {
    suspend fun getItemsDetails(id: String): Resource<Detail>
}