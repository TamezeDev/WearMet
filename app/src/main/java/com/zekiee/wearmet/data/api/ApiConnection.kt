package com.zekiee.wearmet.data.api

import com.zekiee.wearmet.data.api.OpenMeteoApi.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConnection {

    private val basicInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder().addHeader("Accept", "application/json").build()
        chain.proceed(request)
    }

    private val httpClient =
        OkHttpClient.Builder().addInterceptor(basicInterceptor).connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS).build()

    fun retrofit(baseUrl: String): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}