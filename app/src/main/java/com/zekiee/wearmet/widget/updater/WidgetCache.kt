package com.zekiee.wearmet.widget.updater

import android.content.Context
import com.zekiee.wearmet.domain.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class CachedWeather(
    val locationId: String,
    val savedAt: Long,
    val weather: Weather
)

object WidgetCache {
    private val json = Json { ignoreUnknownKeys = true }

    private fun file(context: Context) =
        File(context.applicationContext.filesDir, "widget_weather.json")

    suspend fun save(context: Context, locationId: String, weather: Weather) =
        withContext(Dispatchers.IO) {
            runCatching {
                val target = file(context)
                val tmp = File(target.parentFile, "widget_weather.json.tmp")
                val cached = CachedWeather(locationId, System.currentTimeMillis(), weather)
                tmp.writeText(json.encodeToString(cached))
                tmp.renameTo(target)
            }
        }

    suspend fun load(context: Context, locationId: String): CachedWeather? =
        withContext(Dispatchers.IO) {
            runCatching {
                val f = file(context)
                if (!f.exists()) return@runCatching null
                json.decodeFromString<CachedWeather>(f.readText())
                    .takeIf { it.locationId == locationId }
            }.getOrNull()
        }
}