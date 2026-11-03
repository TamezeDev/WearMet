package com.zekiee.wearmet.data.api

import com.zekiee.wearmet.data.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApiService {
    // ========= GET FULL DATA FROM LOCATION SELECTED ============
    @GET("v1/forecast")
    suspend fun obtenerTiempo(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String = OpenMeteoVars.CURRENT,
        @Query("hourly") hourly: String = OpenMeteoVars.HOURLY,
        @Query("daily") daily: String = OpenMeteoVars.DAILY,
        @Query("forecast_days") nextDays: Int = 7,
        @Query("timezone") timezone: String = "auto"
    ): Response<WeatherResponse>
}