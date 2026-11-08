@file:UseSerializers(LocalDateTimeSerializer::class)
package com.zekiee.wearmet.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers


import java.time.LocalDateTime
@Serializable
data class HourlyForecast(
    val time: LocalDateTime,
    val temperature: Double,
    val precipitationProbability: Int,
    val weatherCode: Int,
    val windSpeed: Double,
    val windDirection: Int
)
