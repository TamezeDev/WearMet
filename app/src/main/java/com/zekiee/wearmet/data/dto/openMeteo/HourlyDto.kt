package com.zekiee.wearmet.data.dto.openMeteo

import com.google.gson.annotations.SerializedName
import com.zekiee.wearmet.domain.model.HourlyForecast
import java.time.LocalDateTime

data class HourlyDto(
    val time: List<String>?,
    @SerializedName("temperature_2m") val temperature: List<Double>?,
    @SerializedName("precipitation_probability") val precipitationProbability: List<Int>?,
    @SerializedName("weather_code") val weatherCode: List<Int>?
)

fun HourlyDto.toDomain(): List<HourlyForecast> {
    val times = time ?: return emptyList()
    return times.indices.mapNotNull { i ->
        HourlyForecast(
            time = LocalDateTime.parse(times[i]),
            temperature = temperature?.getOrNull(i) ?: return@mapNotNull null,
            precipitationProbability = precipitationProbability?.getOrNull(i)
                ?: return@mapNotNull null,
            weatherCode = weatherCode?.getOrNull(i) ?: return@mapNotNull null
        )
    }
}
