package com.android.basicstructure.core.util.dataBinding

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.android.basicstructure.R
import com.android.basicstructure.core.util.getParentActivity
import com.android.basicstructure.model.response.CartData

/**
 * Created by Jeetesh surana.
 */

@BindingAdapter("setCartAmount")
fun setCartAmount(layout: AppCompatTextView?, data: CartData?) {
    if (layout != null && data != null) {
        layout.text = layout.getParentActivity()?.resources?.getString(
            R.string.amount, data.itemAmount.toString()
        )
    }
}