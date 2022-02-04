package com.android.basicstructure.core.util

import android.os.SystemClock
import android.view.View
/**
 * Created by JeeteshSurana.
 */
class OnFilterClickListener(private val block: () -> Unit) : View.OnClickListener {
    private var lastClickTime = 0L
    override fun onClick(view: View) {
        if (SystemClock.elapsedRealtime() - lastClickTime < 1000) {
            return
        }
        lastClickTime = SystemClock.elapsedRealtime()
        block()
    }
}

fun View.setOnFilterClickListener(block: () -> Unit) {
    setOnClickListener(OnFilterClickListener(block))
}
