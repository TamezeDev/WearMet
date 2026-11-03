package com.zekiee.wearmet.domain.model

import java.time.LocalDate
import java.time.LocalDateTime
data class DailyForecast(
    val date: LocalDate,
    val weatherCode: Int,
    val temperatureMax: Double,
    val temperatureMin: Double,
    val sunrise: LocalDateTime,
    val sunset: LocalDateTime,
    val uvIndexMax: Double,
    val precipitationSum: Double,
    val precipitationProbabilityMax: Int,
    val windSpeedMax: Double
)