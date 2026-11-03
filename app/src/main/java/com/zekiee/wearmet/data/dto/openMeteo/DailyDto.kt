package com.zekiee.wearmet.data.dto.openMeteo

import com.google.gson.annotations.SerializedName
import com.zekiee.wearmet.domain.model.DailyForecast
import java.time.LocalDate
import java.time.LocalDateTime

data class DailyDto(
    val time: List<String>?,
    @SerializedName("weather_code") val weatherCode: List<Int>?,
    @SerializedName("temperature_2m_max") val temperatureMax: List<Double>?,
    @SerializedName("temperature_2m_min") val temperatureMin: List<Double>?,
    val sunrise: List<String>?,
    val sunset: List<String>?,
    @SerializedName("uv_index_max") val uvIndexMax: List<Double>?,
    @SerializedName("precipitation_sum") val precipitationSum: List<Double>?,
    @SerializedName("precipitation_probability_max") val precipitationProbabilityMax: List<Int>?,
    @SerializedName("wind_speed_10m_max") val windSpeedMax: List<Double>?
)

fun DailyDto.toDomain(): List<DailyForecast> {
    val dates = time ?: return emptyList()
    return dates.indices.mapNotNull { i ->
        DailyForecast(
            date = LocalDate.parse(dates[i]),
            weatherCode = weatherCode?.getOrNull(i) ?: return@mapNotNull null,
            temperatureMax = temperatureMax?.getOrNull(i) ?: return@mapNotNull null,
            temperatureMin = temperatureMin?.getOrNull(i) ?: return@mapNotNull null,
            sunrise = LocalDateTime.parse(sunrise?.getOrNull(i) ?: return@mapNotNull null),
            sunset = LocalDateTime.parse(sunset?.getOrNull(i) ?: return@mapNotNull null),
            uvIndexMax = uvIndexMax?.getOrNull(i) ?: return@mapNotNull null,
            precipitationSum = precipitationSum?.getOrNull(i) ?: return@mapNotNull null,
            precipitationProbabilityMax = precipitationProbabilityMax?.getOrNull(i)
                ?: return@mapNotNull null,
            windSpeedMax = windSpeedMax?.getOrNull(i) ?: return@mapNotNull null
        )
    }
}
