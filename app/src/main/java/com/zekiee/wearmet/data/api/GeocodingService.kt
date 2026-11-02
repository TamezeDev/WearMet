package com.zekiee.wearmet.data.api

import com.zekiee.wearmet.data.response.GeocodingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingService {
    // ========= GET LATITUDE AND LONGITUDE LOCATION ============
    @GET("v1/search")
    suspend fun searchLocation(
        @Query("name") name: String,
        @Query("count") count: Int = 5,
        @Query("language") language: String = "es"
    ): Response<GeocodingResponse>
}