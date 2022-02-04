package com.android.basicstructure.app

import android.app.Application
import com.android.basicstructure.BuildConfig
import com.android.basicstructure.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.logging.Level

/**
 * Created by JeeteshSurana.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // start Koin!
        startKoin {
//            androidContext(this@App)
//            androidLogger()
//            printLogger()
            // declare modules
            modules(appModule)
        }
    }
}