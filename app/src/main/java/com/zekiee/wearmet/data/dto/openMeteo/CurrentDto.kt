package com.zekiee.wearmet.data.dto.openMeteo

import com.google.gson.annotations.SerializedName
import com.zekiee.wearmet.domain.model.CurrentWeather
import java.time.LocalDateTime

data class CurrentDto(
    val time: String?,
    @SerializedName("temperature_2m") val temperature: Double?,
    @SerializedName("relative_humidity_2m") val humidity: Int?,
    @SerializedName("apparent_temperature") val feelsLike: Double?,
    @SerializedName("is_day") val isDay: Int?,
    val precipitation: Double?,
    @SerializedName("weather_code") val weatherCode: Int?,
    @SerializedName("cloud_cover") val cloudCover: Int?,
    @SerializedName("pressure_msl") val pressure: Double?,
    @SerializedName("wind_speed_10m") val windSpeed: Double?,
    @SerializedName("wind_direction_10m") val windDirection: Int?,
    @SerializedName("wind_gusts_10m") val windGusts: Double?,
)

fun CurrentDto.toDomain(): CurrentWeather? {
    val currentTime = LocalDateTime.parse(time ?: return null)
    val currentTemperature = temperature ?: return null
    val currentFeelsLike = feelsLike ?: return null
    val currentHumidity = humidity ?: return null
    val currentIsDay = (isDay ?: return null) == 1
    val currentPrecipitation = precipitation ?: return null
    val currentWeatherCode = weatherCode ?: return null
    val currentCloudCover = cloudCover ?: return null
    val currentPressure = pressure ?: return null
    val currentWindSpeed = windSpeed ?: return null
    val currentWindDirection = windDirection ?: return null
    val currentWindGusts = windGusts ?: return null

    return CurrentWeather(
        currentTime,
        currentTemperature,
        currentFeelsLike,
        currentHumidity,
        currentIsDay,
        currentPrecipitation,
        currentWeatherCode,
        currentCloudCover,
        currentPressure,
        currentWindSpeed,
        currentWindDirection,
        currentWindGusts
    )
}


