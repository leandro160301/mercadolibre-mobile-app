package com.jws.jwsapi.feature_search.domain

interface SearchRepository {
    fun addRecentSearch(value: String)
    fun getRecentSearch(): List<Search>
}