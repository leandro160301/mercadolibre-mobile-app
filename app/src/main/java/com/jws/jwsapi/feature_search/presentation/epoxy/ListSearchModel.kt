package com.jws.jwsapi.feature_search.presentation.epoxy

import com.jws.jwsapi.R
import com.jws.jwsapi.databinding.ItemSearchBinding
import com.jws.jwsapi.feature_search.domain.Search
import com.jws.jwsapi.shared.ViewBindingKotlinModel

data class ListSearchModel(
    private val search: Search,
    private val onSearchSelected: (value: String) -> Unit,
) : ViewBindingKotlinModel<ItemSearchBinding>(R.layout.item_search) {

    override fun ItemSearchBinding.bind() {
        textViewQuery.text = search.recentSearch

        linearLayout.setOnClickListener { onSearchSelected(search.recentSearch) }
    }


}