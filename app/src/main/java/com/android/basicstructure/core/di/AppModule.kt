package com.android.basicstructure.core.di

import android.content.Context
import com.android.basicstructure.connection.APIClient
import com.android.basicstructure.core.ui.BaseViewModel
import com.android.basicstructure.core.util.NetworkManager
import com.android.basicstructure.core.util.PreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by JeeteshSurana.
 */

val appModule = module {

    single { NetworkManager(androidContext()) }
    single { APIClient(androidContext()) }
    single {
        PreferenceManager(
            androidContext().getSharedPreferences(
                androidContext().applicationContext.packageName,
                Context.MODE_PRIVATE
            )
        )
    }

    //ViewModels
    viewModel { BaseViewModel() }
}
