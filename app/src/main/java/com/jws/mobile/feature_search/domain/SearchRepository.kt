package com.jws.mobile.feature_search.domain

interface SearchRepository {
    suspend fun addRecentSearch(value: String)
    suspend fun getRecentSearch(): List<Search>
}