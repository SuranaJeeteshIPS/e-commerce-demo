package com.android.basicstructure.connection

import com.android.basicstructure.connection.HttpClientManager.defaultHttpClient
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Jeetesh Surana.
 */

inline fun <reified T : Any> mApiClient(): T {
    val retrofit = Retrofit.Builder()
        .client(defaultHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}

object HttpClientManager {
    val defaultHttpClient: OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
        .build()
}