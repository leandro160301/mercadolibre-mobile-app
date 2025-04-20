package com.jws.mobile.feature_preview.domain

import com.jws.mobile.feature_preview.domain.items.ItemsResponse
import com.jws.mobile.feature_preview.domain.preview.Preview
import com.jws.mobile.core.utils.Resource

interface PreviewRepository {
    suspend fun getItemsBySeller(query: String? = null): Resource<ItemsResponse>
    suspend fun getItemsDetails(ids: String): Resource<List<Preview>>
}