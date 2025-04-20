package com.jws.mobile.feature_search.data

import com.jws.mobile.core.helpers.PreferencesHelper
import com.jws.mobile.feature_search.domain.Search
import com.jws.mobile.feature_search.domain.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val preferencesHelper: PreferencesHelper
) : SearchRepository {
    override fun addRecentSearch(value: String) {
        val currentList =
            preferencesHelper.getListModel(RECENT_SEARCHES_KEY, Search::class.java)?.toMutableList()
                ?: mutableListOf()
        currentList.removeAll { it.recentSearch == value }
        currentList.add(0, Search(value))
        preferencesHelper.putListModel(RECENT_SEARCHES_KEY, currentList)
    }

    override fun getRecentSearch(): List<Search> {
        val list = preferencesHelper.getListModel(RECENT_SEARCHES_KEY, Search::class.java)

        return if (list.isNullOrEmpty()) {
            val defaultList = listOf(Search("iphone"), Search("autos"))
            preferencesHelper.putListModel(RECENT_SEARCHES_KEY, defaultList)
            defaultList
        } else {
            list
        }
    }

    companion object {
        private const val RECENT_SEARCHES_KEY = "recent_searches"
    }
}