package com.zekiee.wearmet.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.zekiee.wearmet.data.api.ManageApiService
import com.zekiee.wearmet.data.dto.geoCoding.toDomain
import com.zekiee.wearmet.domain.result.LocationResult
import java.io.IOException
import java.util.concurrent.CancellationException

class LocationRepository {

    suspend fun getLocations(searchLocation: String): LocationResult {
        return try {
            val response = ManageApiService.geocodingApiService.searchLocation(searchLocation)
            val body = response.body()

            if (response.isSuccessful) {
                LocationResult.Success(body?.results.orEmpty().mapNotNull { it.toDomain() })
            } else {
                Log.w(TAG, "HTTP ${response.code()}")
                LocationResult.ServerError
            }

        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            Log.e(TAG, "No connection available", e)
            LocationResult.NetworkError
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error", e)
            LocationResult.UnknownError
        }
    }
}