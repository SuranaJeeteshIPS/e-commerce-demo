package com.android.basicstructure.core.util.customSnackBar

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.android.basicstructure.R
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.ContentViewCallback


/**
 * Created by Abhin.
 */

class CustomSnackBar(
    parent: ViewGroup,
    content: View,
    callback: com.google.android.material.snackbar.ContentViewCallback
) : BaseTransientBottomBar<CustomSnackBar>(parent, content, callback) {

    fun setText(text: CharSequence): CustomSnackBar {
        val textView = getView().findViewById(R.id.snack_bar_text) as AppCompatTextView
        textView.text = text
        return this
    }

    fun setAction(text: CharSequence, listener: View.OnClickListener): CustomSnackBar {
        val actionView = getView().findViewById(R.id.snack_bar_btn) as AppCompatButton
        actionView.text = text
        actionView.visibility = View.VISIBLE
        actionView.setOnClickListener { view ->
            listener.onClick(view)
            dismiss()
        }
        return this
    }

    companion object {

        fun make(context: Context, view: View, @Duration duration: Int): CustomSnackBar {
            val parent = view.findSuitableParent()
                ?: throw IllegalArgumentException(context.resources.getString(R.string.error_custom_snackbar))
            val viewCallback = SnackBarView(context)
            val customView = LayoutInflater.from(view.context)
                .inflate(R.layout.custom_snack_bar, parent, false) as SnackBarView
            val customSnackBar = CustomSnackBar(parent, customView, viewCallback)
            customSnackBar.getView().background =
                ContextCompat.getDrawable(context, android.R.color.transparent)
            customSnackBar.duration = duration
            return customSnackBar
        }
    }
}

class SnackBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    private val buttonDismiss: AppCompatButton
    private var txtMessage: AppCompatTextView

    init {
        View.inflate(context, R.layout.view_snackbar, this)
        clipToPadding = false
        clipChildren = false
        isDuplicateParentStateEnabled = true
        this.buttonDismiss = findViewById(R.id.snack_bar_btn)
        this.txtMessage = findViewById(R.id.snack_bar_text)
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        val scaleX = ObjectAnimator.ofFloat(buttonDismiss, View.SCALE_X, 0f, 1f)
        val scaleY = ObjectAnimator.ofFloat(buttonDismiss, View.SCALE_Y, 0f, 1f)
        val animatorSet = AnimatorSet().apply {
            interpolator = OvershootInterpolator()
            setDuration(500)
            playTogether(
                scaleX,
                scaleY
            )
        }
        animatorSet.start()
    }

    override fun animateContentOut(
        delay: Int,
        duration: Int
    ) {
    }
}

internal fun View?.findSuitableParent(): ViewGroup? {
    var view = this
    var fallback: ViewGroup? = null
    do {
        if (view is CoordinatorLayout) {
            return view
        } else if (view is FrameLayout) {
            if (view.id == android.R.id.content) {
                return view
            } else {
                fallback = view
            }
        }

        if (view != null) {
            val parent = view.parent
            view = if (parent is View) parent else null
        }
    } while (view != null)
    return fallback
}