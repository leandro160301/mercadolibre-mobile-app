package com.jws.jwsapi.feature_detail.domain

import javax.inject.Inject

class FetchDetailByIdUseCase @Inject constructor(
    private val detailRepository: DetailRepository
) {
    suspend operator fun invoke(id: String) = detailRepository.getItemsDetails(id)
}