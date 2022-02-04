package com.android.basicstructure.core.ui

import androidx.fragment.app.Fragment
import com.android.basicstructure.core.util.hideKeyboard

/**
 * Created by JeeteshSurana.
 */

abstract class BaseFragment : Fragment() {
    override fun onPause() {
        activity?.hideKeyboard()
        super.onPause()
    }

    override fun onDestroy() {
        activity?.hideKeyboard()
        super.onDestroy()
    }
}