package com.zekiee.wearmet.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val current: CurrentWeather,
    val hourly: List<HourlyForecast>,
    val daily: List<DailyForecast>
)