package com.jws.mobile.feature_search.domain

import javax.inject.Inject

class AddRecentSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(value: String) = searchRepository.addRecentSearch(value)
}