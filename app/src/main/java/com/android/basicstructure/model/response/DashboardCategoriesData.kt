package com.android.basicstructure.model.response

data class DashboardCategoriesData(
    var id: String? = null,
    var title: String? = null,
    var rating: Float? = null,
    var isSelected: Boolean = false,
    var itemCount: Int? = null,
    var itemAmount: Int? = null,
    var url: String? = null
)
