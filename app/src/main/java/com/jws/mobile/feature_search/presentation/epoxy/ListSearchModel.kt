package com.jws.mobile.feature_search.presentation.epoxy

import com.jws.mobile.R
import com.jws.mobile.databinding.ItemSearchBinding
import com.jws.mobile.feature_search.domain.Search
import com.jws.mobile.core.helpers.ViewBindingKotlinModel

data class ListSearchModel(
    private val search: Search,
    private val onSearchSelected: (value: String) -> Unit,
) : ViewBindingKotlinModel<ItemSearchBinding>(R.layout.item_search) {

    override fun ItemSearchBinding.bind() {
        textViewQuery.text = search.recentSearch

        linearLayout.setOnClickListener { onSearchSelected(search.recentSearch) }
    }


}