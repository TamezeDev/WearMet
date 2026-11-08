package com.zekiee.wearmet.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.zekiee.wearmet.domain.model.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume
import kotlin.time.Duration.Companion.milliseconds

class DeviceLocationProvider(private val context: Context) {
    private val client = LocationServices.getFusedLocationProviderClient(context)

    suspend fun currentLocation(fallbackName: String): Location? {
        val loc = withTimeoutOrNull(10_000.milliseconds) { fetchCurrent() } ?: fetchLast()
        if (loc == null) {
            Log.d("WearMetLocation", "No location from current or last")
            return null
        }
        Log.d("WearMetLocation", "Got ${loc.latitude}, ${loc.longitude}")

        val address = withTimeoutOrNull(3_000.milliseconds) { reverseGeocode(loc.latitude, loc.longitude) }
        return Location(
            id = ("${"%.2f".format(loc.latitude)},${"%.2f".format(loc.longitude)}").hashCode(),
            name = address?.locality ?: address?.subAdminArea ?: address?.adminArea ?: fallbackName,
            latitude = loc.latitude,
            longitude = loc.longitude,
            countryCode = address?.countryCode.orEmpty(),
            region = address?.adminArea
        )
    }

    @SuppressLint("MissingPermission")
    private suspend fun fetchCurrent(): android.location.Location? =
        suspendCancellableCoroutine { cont ->
            val cts = CancellationTokenSource()
            cont.invokeOnCancellation { cts.cancel() }
            client.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, cts.token)
                .addOnSuccessListener { cont.resume(it) }
                .addOnFailureListener { cont.resume(null) }
        }

    @SuppressLint("MissingPermission")
    private suspend fun fetchLast(): android.location.Location? =
        suspendCancellableCoroutine { cont ->
            client.lastLocation
                .addOnSuccessListener { cont.resume(it) }
                .addOnFailureListener { cont.resume(null) }
        }

    private suspend fun reverseGeocode(lat: Double, lon: Double): Address? {
        if (!Geocoder.isPresent()) return null
        val geocoder = Geocoder(context)
        return if (Build.VERSION.SDK_INT >= 33) {
            suspendCancellableCoroutine { cont ->
                geocoder.getFromLocation(lat, lon, 1, object : Geocoder.GeocodeListener {
                    override fun onGeocode(addresses: MutableList<Address>) {
                        cont.resume(addresses.firstOrNull())
                    }
                    override fun onError(errorMessage: String?) { cont.resume(null) }
                })
            }
        } else {
            withContext(Dispatchers.IO) {
                @Suppress("DEPRECATION")
                runCatching { geocoder.getFromLocation(lat, lon, 1)?.firstOrNull() }.getOrNull()
            }
        }
    }
}