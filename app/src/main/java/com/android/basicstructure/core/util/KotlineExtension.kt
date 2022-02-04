package com.android.basicstructure.core.util

import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.android.basicstructure.R
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.*

/**
 * Created by JeeteshSurana.
 */

fun <T> mutableLiveData(defaultValue: T? = null): MutableLiveData<T> {
    val data = MutableLiveData<T>()
    if (defaultValue != null) {
        data.value = defaultValue
    }
    return data
}

fun View.getParentActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

@BindingAdapter("android:priceText")
fun setPriceText(textView: AppCompatTextView?, price: String?) {
    val parentActivity: AppCompatActivity? = textView!!.getParentActivity()
    if (!price.isNullOrEmpty() && textView.getParentActivity() != null) {
        val nf: NumberFormat = NumberFormat.getInstance(Locale.ITALY)
    }
}

@BindingAdapter("android:srcImage")
fun setSrcImage(productImage: AppCompatImageView?, url: Int?) {
    val parentActivity: AppCompatActivity? = productImage?.getParentActivity()
    if (url != 0 && productImage?.getParentActivity() != null) {
        parentActivity?.applicationContext?.let { Glide.with(it).load(url).into(productImage) }
    }
}

@BindingAdapter("android:setEpoch")
fun setEpoch(timeText: AppCompatTextView?, time: String?) {
    if (!time.isNullOrEmpty()) {
    }
}

@BindingAdapter("android:setDate")
fun setDate(timeText: AppCompatTextView?, time: String?) {
    if (!time.isNullOrEmpty()) {
        timeText?.text = getConvertDate(time)
    }
}

@BindingAdapter(value = ["setDes"], requireAll = true)
fun setDes(appCompatTextView: AppCompatTextView?, totalValues: String?) {
    if (!totalValues.isNullOrEmpty()) {
        val mContext = appCompatTextView?.context
        mContext?.let {
        }
    }
}
