package com.zekiee.wearmet.data.response

import com.zekiee.wearmet.data.dto.openMeteo.CurrentDto
import com.zekiee.wearmet.data.dto.openMeteo.DailyDto
import com.zekiee.wearmet.data.dto.openMeteo.HourlyDto
import com.zekiee.wearmet.data.dto.openMeteo.toDomain
import com.zekiee.wearmet.domain.model.Weather

data class WeatherResponse(
    val current: CurrentDto?,
    val hourly: HourlyDto?,
    val daily: DailyDto?
)

fun WeatherResponse.toDomain(): Weather? {
    val currentWeather = current?.toDomain() ?: return null
    return Weather(
        current = currentWeather,
        hourly = hourly?.toDomain().orEmpty(),
        daily = daily?.toDomain().orEmpty()
    )
}