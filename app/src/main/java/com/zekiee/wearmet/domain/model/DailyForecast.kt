@file:UseSerializers(LocalDateTimeSerializer::class, LocalDateSerializer::class)package com.zekiee.wearmet.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
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