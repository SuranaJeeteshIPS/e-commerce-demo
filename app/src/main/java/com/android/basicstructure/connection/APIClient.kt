package com.android.basicstructure.connection

import android.content.Context
import com.android.basicstructure.core.util.PreferenceManager
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Created by JeeteshSurana.
 * Api Client for retrofit Instance with Base Url
 */

class APIClient(var mContext: Context): KoinComponent {

    var mRetrofit: Retrofit? = null
    val mPreferenceManager: PreferenceManager by inject()

    /**
     * getClient Retorfit instance
     * @param mContext use for store caching
     */
    fun getClient(): RetrofitInterface {
        if (mRetrofit == null) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            mRetrofit = Retrofit.Builder().baseUrl(baseURL).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(getOkHttpClient()).build()
        }
        return mRetrofit!!.create(RetrofitInterface::class.java)
    }

    /**
     * getOkHttpClient
     */
    private fun getOkHttpClient(): OkHttpClient {
        val networkCacheInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())
            val cacheControl = CacheControl.Builder().maxAge(2, TimeUnit.HOURS).build()
            response.newBuilder().header(cacheControls, cacheControl.toString()).build()
        }
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().connectTimeout(3, TimeUnit.MINUTES).readTimeout(90, TimeUnit.SECONDS).writeTimeout(45, TimeUnit.SECONDS).addInterceptor(loggingInterceptor).addNetworkInterceptor(networkCacheInterceptor).build()
    }
}
