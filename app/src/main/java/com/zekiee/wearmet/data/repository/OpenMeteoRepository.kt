package com.zekiee.wearmet.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.zekiee.wearmet.data.api.ManageApiService
import com.zekiee.wearmet.data.response.toDomain
import com.zekiee.wearmet.domain.result.WeatherResult
import kotlinx.coroutines.CancellationException
import java.io.IOException

class OpenMeteoRepository {
    suspend fun getWeatherData(latitude: Double, longitude: Double): WeatherResult {
        return try {
            val response = ManageApiService.openMeteoApiService.obtenerTiempo(longitude, latitude)

            if (response.isSuccessful) {
                val weather = response.body()?.toDomain()
                if (weather != null) WeatherResult.Success(weather)
                else WeatherResult.ServerError
            } else {
                Log.w(TAG, "HTTP ${response.code()}")
                WeatherResult.ServerError
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            Log.e(TAG, "No connection available", e)
            WeatherResult.NetworkError
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error", e)
            WeatherResult.UnknownError
        }
    }
}