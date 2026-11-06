package com.zekiee.wearmet.domain.model

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

fun Weather.nextHours(count: Int = 24): List<HourlyForecast> {
    val startHour = current.time.truncatedTo(ChronoUnit.HOURS)
    return hourly.filter { !it.time.isBefore(startHour) }.take(count)
}

fun List<DailyForecast>.isDaytime(time: LocalDateTime): Boolean {
    val day = firstOrNull { it.date == time.toLocalDate() } ?: return true
    return time >= day.sunrise && time < day.sunset
}