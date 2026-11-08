package com.zekiee.wearmet.widget.state

import android.content.Context
import android.util.Log
import com.zekiee.wearmet.R
import com.zekiee.wearmet.data.repository.OpenMeteoRepository
import com.zekiee.wearmet.domain.result.WeatherResult
import com.zekiee.wearmet.widget.updater.WidgetCache
import com.zekiee.wearmet.widget.updater.WidgetLocationResolver
import com.zekiee.wearmet.widget.updater.WidgetUpdateScheduler

class WidgetStateLoader(private val context: Context) {
    suspend fun load(): WidgetState {
        val location = WidgetLocationResolver.resolve(context)
            ?: return WidgetState.Message(context.getString(R.string.widget_no_location))
        val locationKey = location.id.toString()

        return when (val result =
            OpenMeteoRepository().getWeatherData(location.latitude, location.longitude)) {
            is WeatherResult.Success -> {
                WidgetCache.save(context, locationKey, result.weather)
                WidgetState.Data(location.name, result.weather)
            }

            else -> {
                Log.w("WearMetWidget", "Widget load failed: $result")
                if (result == WeatherResult.NetworkError) {
                    WidgetUpdateScheduler.retryWhenOnline(context)
                }
                val cached = WidgetCache.load(context, locationKey)
                if (cached != null) {
                    WidgetState.Data(location.name, cached.weather)
                } else {
                    WidgetState.Message(context.getString(R.string.widget_error))
                }
            }
        }
    }
}