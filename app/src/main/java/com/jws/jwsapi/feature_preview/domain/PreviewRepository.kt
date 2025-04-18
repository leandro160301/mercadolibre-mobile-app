package com.jws.jwsapi.feature_preview.domain

import com.jws.jwsapi.feature_preview.domain.items.ItemsResponse
import com.jws.jwsapi.shared.Resource

interface PreviewRepository {
    suspend fun getItemsBySeller(): Resource<ItemsResponse>
}