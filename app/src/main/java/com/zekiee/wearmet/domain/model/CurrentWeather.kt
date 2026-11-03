package com.zekiee.wearmet.domain.model

import java.time.LocalDateTime

data class CurrentWeather(
    val time: LocalDateTime,
    val temperature: Double,
    val feelsLike: Double,
    val humidity: Int,
    val isDay: Boolean,
    val precipitation: Double,
    val weatherCode: Int,
    val cloudCover: Int,
    val pressure: Double,
    val windSpeed: Double,
    val windDirection: Int,
    val windGusts: Double
)
