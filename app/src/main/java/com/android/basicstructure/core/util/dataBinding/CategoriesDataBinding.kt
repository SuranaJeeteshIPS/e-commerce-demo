package com.android.basicstructure.core.util.dataBinding

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.android.basicstructure.model.response.DashboardCategoriesData

/**
 * Created by Jeetesh surana.
 */

@BindingAdapter("setSelection")
fun setSelection(layout: AppCompatTextView?, data: DashboardCategoriesData?) {
    if (layout != null && data != null) {
        layout.isSelected = data.isSelected
    }
}
