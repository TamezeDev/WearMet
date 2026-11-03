package com.zekiee.wearmet.domain.result

import com.zekiee.wearmet.domain.model.Weather

sealed class WeatherResult {
    data class Success(val weather: Weather) : WeatherResult()
    data object NetworkError : WeatherResult()
    data object ServerError : WeatherResult()
    data object UnknownError : WeatherResult()
}