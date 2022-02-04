package com.android.basicstructure.model.response

data class CartData(
    var id: String? = null,
    var title: String? = null,
    var rating: Float? = null,
    var itemCount: Int? = null,
    var itemAmount: Int? = null,
    var url: String? = null
)
