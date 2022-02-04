package com.android.basicstructure.core.ui

import androidx.appcompat.app.AppCompatActivity
import com.android.basicstructure.core.util.hideKeyboard

/**
 * Created by JeeteshSurana.
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onPause() {
        hideKeyboard()
        super.onPause()
    }

    override fun onDestroy() {
        hideKeyboard()
        super.onDestroy()
    }
}