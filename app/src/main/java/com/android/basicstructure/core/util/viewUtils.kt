package com.android.basicstructure.core.util

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.android.basicstructure.R
import com.android.basicstructure.core.util.customSnackBar.CustomSnackBar
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Jeetesh surana.
 */


fun View.snackBarWithAnchor(
    message: String,
    anchorView: View
) {
    showSnackBar(
        this,
        message,
        anchorView
    )
}

fun View.snackBar(
    message: String? = null,
    isAction: Boolean = false
) {
    val mCustomSnackBar = CustomSnackBar.make(
        context,
        this,
        Snackbar.LENGTH_LONG
    )
    val params: ViewGroup.LayoutParams = mCustomSnackBar.view.layoutParams
    when (params) {
        is CoordinatorLayout.LayoutParams -> {
            params.gravity = Gravity.TOP
        }
        else -> {
            (params as FrameLayout.LayoutParams).gravity = Gravity.TOP
        }
    }
    mCustomSnackBar.view.layoutParams = params
    message?.let { mCustomSnackBar.setText(it) }
    if (isAction) {
        mCustomSnackBar.setAction(
            context.resources.getString(R.string.ok),
            View.OnClickListener { mCustomSnackBar.dismiss() })
    }
    mCustomSnackBar.show()
}

interface SnackBarActionListener {
    fun onDismissed()
}

fun View.snackBarWithCallBack(
    message: String? = null,
    isAction: Boolean = false,
    snackBarActionListener: SnackBarActionListener? = null
) {
    val mCustomSnackBar = CustomSnackBar.make(
        context,
        this,
        Snackbar.LENGTH_LONG
    )
    val params: ViewGroup.LayoutParams = mCustomSnackBar.view.layoutParams
    when (params) {
        is CoordinatorLayout.LayoutParams -> {
            params.gravity = Gravity.TOP
        }
        else -> {
            (params as FrameLayout.LayoutParams).gravity = Gravity.TOP
        }
    }
    mCustomSnackBar.view.layoutParams = params
    message?.let { mCustomSnackBar.setText(it) }
    if (isAction) {
        mCustomSnackBar.setAction(
            context.resources.getString(R.string.ok),
            View.OnClickListener { mCustomSnackBar.dismiss() })
    }
    mCustomSnackBar.addCallback(object : BaseTransientBottomBar.BaseCallback<CustomSnackBar>() {
        override fun onDismissed(
            transientBottomBar: CustomSnackBar?,
            event: Int
        ) {
            super.onDismissed(
                transientBottomBar,
                event
            )
            snackBarActionListener?.onDismissed()
        }
    })
    mCustomSnackBar.show()
}


fun showSnackBar(
    view: View,
    message: String? = null,
    anchor: View?,
    isAction: Boolean = false
) {
    val mCustomSnackBar = CustomSnackBar.make(
        view.context,
        view,
        Snackbar.LENGTH_LONG
    )
    val params: ViewGroup.LayoutParams = mCustomSnackBar.view.layoutParams
    when (params) {
        is CoordinatorLayout.LayoutParams -> {
            params.gravity = Gravity.TOP
        }
        else -> {
            (params as FrameLayout.LayoutParams).gravity = Gravity.TOP
        }
    }
    mCustomSnackBar.view.layoutParams = params
    mCustomSnackBar.anchorView = anchor
    message?.let { mCustomSnackBar.setText(it) }
    if (isAction) {
        mCustomSnackBar.setAction(
            view.context.resources.getString(R.string.ok),
            View.OnClickListener { mCustomSnackBar.dismiss() })
    }
    mCustomSnackBar.show()
}
