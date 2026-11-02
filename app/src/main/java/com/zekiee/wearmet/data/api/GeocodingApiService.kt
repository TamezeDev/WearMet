package com.zekiee.wearmet.data.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object GeocodingApiService {

    private const val BASE_URL = "https://geocoding-api.open-meteo.com/"

    private val basicInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder().addHeader("Accept", "application/json").build()
        chain.proceed(request)
    }

    private val httpClient =
        OkHttpClient.Builder().addInterceptor(basicInterceptor).connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS).build()

    val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(httpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()

}