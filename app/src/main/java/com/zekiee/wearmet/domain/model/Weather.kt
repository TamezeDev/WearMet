package com.zekiee.wearmet.domain.model

data class Weather(
    val current: CurrentWeather,
    val hourly: List<HourlyForecast>,
    val daily: List<DailyForecast>
)