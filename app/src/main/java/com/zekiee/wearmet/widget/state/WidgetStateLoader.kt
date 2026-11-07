package com.zekiee.wearmet.widget.state

import android.content.Context
import android.util.Log
import com.zekiee.wearmet.R
import com.zekiee.wearmet.data.repository.OpenMeteoRepository
import com.zekiee.wearmet.domain.result.WeatherResult
import com.zekiee.wearmet.local.data.dataStore.LastLocationStore

class WidgetStateLoader(private val context: Context) {
    suspend fun load(): WidgetState {
        val location = LastLocationStore(context).get()
            ?: return WidgetState.Message(context.getString(R.string.widget_no_location))
        return when (val result =
            OpenMeteoRepository().getWeatherData(location.latitude, location.longitude)) {
            is WeatherResult.Success -> WidgetState.Data(location.name, result.weather)
            else -> {
                Log.w("WearMetWidget", "Widget load failed: $result")
                WidgetState.Message(context.getString(R.string.widget_error))
            }
        }
    }
}