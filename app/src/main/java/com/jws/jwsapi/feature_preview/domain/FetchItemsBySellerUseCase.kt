package com.jws.jwsapi.feature_preview.domain

import javax.inject.Inject

class FetchItemsBySellerUseCase @Inject constructor(
    private val previewRepository: PreviewRepository
) {
    suspend operator fun invoke(query: String? = null) = previewRepository.getItemsBySeller(query)
}