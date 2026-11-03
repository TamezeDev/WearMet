package com.zekiee.wearmet.domain.model

import java.time.LocalDateTime

data class HourlyForecast(
    val time: LocalDateTime,
    val temperature: Double,
    val precipitationProbability: Int,
    val weatherCode: Int
)
