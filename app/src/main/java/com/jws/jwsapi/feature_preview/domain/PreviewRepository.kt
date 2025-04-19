package com.jws.jwsapi.feature_preview.domain

import com.jws.jwsapi.feature_preview.domain.items.ItemsResponse
import com.jws.jwsapi.feature_preview.domain.preview.Preview
import com.jws.jwsapi.shared.Resource

interface PreviewRepository {
    suspend fun getItemsBySeller(query: String? = null): Resource<ItemsResponse>
    suspend fun getItemsDetails(ids: String): Resource<List<Preview>>
}