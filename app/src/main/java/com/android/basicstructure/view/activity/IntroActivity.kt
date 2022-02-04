package com.android.basicstructure.view.activity

import android.content.Intent
import android.os.Bundle
import com.android.basicstructure.core.ui.BaseActivity

class IntroActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        intent = Intent(
            this,
            MainActivity::class.java
        ).apply {
            intent.extras?.let { putExtras(it) }
        }
        startActivity(intent)
        finish()
    }
}
