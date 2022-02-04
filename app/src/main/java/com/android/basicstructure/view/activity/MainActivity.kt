package com.android.basicstructure.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android.basicstructure.R
import com.android.basicstructure.core.util.addReplaceFragment
import com.android.basicstructure.view.fragment.CartFragment
import com.android.basicstructure.view.fragment.DashboardFragment

/**
 * Created by JeeteshSurana.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addReplaceFragment(
            R.id.fl_container, DashboardFragment(),
            addFragment = false,
            addToBackStack = false
        )
    }
}
