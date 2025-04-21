package com.jws.mobile.feature_search.domain

import javax.inject.Inject

class GetRecentSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke() = searchRepository.getRecentSearch()
}