package com.jws.mobile.feature_preview.domain

import javax.inject.Inject

class FetchPreviewsByIdUseCase @Inject constructor(
    private val previewRepository: PreviewRepository
) {
    suspend operator fun invoke(ids: String) = previewRepository.getItemsDetails(ids)
}