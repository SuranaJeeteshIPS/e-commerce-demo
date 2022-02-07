package com.android.basicstructure.core.util.dataBinding

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.android.basicstructure.model.response.DashboardCategoriesData
import com.android.basicstructure.model.response.DashboardData

/**
 * Created by Jeetesh surana.
 */

@BindingAdapter("setAddToCart")
fun setAddToCart(layout: View?, data: DashboardData?) {
    if (layout != null && data != null) {
        layout.isSelected = data.isAddedInCart
    }
}
