package com.android.basicstructure.model.response

data class CartData(
    var id: String? = null,
    var title: String? = null,
    var rating: Float? = null,
    var itemAmount: Int = 4,
    var itemQuantity: Int = 1,
    var totalAmount: Int = 0,
    var url: String? = null
) {
    fun getTotalAmountValue(): Int {
        return itemQuantity * itemAmount
    }
}
