package com.zekiee.wearmet.ui.logicUI.weaather

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.zekiee.wearmet.R

enum class WeatherCondition(@StringRes val labelRes: Int) {
    CLEAR(R.string.weather_clear),
    PARTLY_CLOUDY(R.string.weather_partly_cloudy),
    CLOUDY(R.string.weather_cloudy),
    FOG(R.string.weather_fog),
    DRIZZLE(R.string.weather_drizzle),
    RAIN(R.string.weather_rain),
    SNOW(R.string.weather_snow),
    THUNDERSTORM(R.string.weather_thunderstorm);

    @RawRes
    fun lottieRes(isDay: Boolean): Int = when (this) {
        CLEAR -> if (isDay) R.raw.weather_clear_day else R.raw.weather_clear_night
        PARTLY_CLOUDY -> if (isDay) R.raw.weather_partly_day else R.raw.weather_partly_night
        CLOUDY -> R.raw.weather_cloudy
        FOG -> R.raw.weather_fog
        DRIZZLE, RAIN -> R.raw.weather_rain
        SNOW -> R.raw.weather_snow
        THUNDERSTORM -> R.raw.weather_thunderstorm
    }

    companion object {
        fun fromCode(code: Int): WeatherCondition = when (code) {
            0, 1 -> CLEAR
            2 -> PARTLY_CLOUDY
            3 -> CLOUDY
            45, 48 -> FOG
            in 51..57 -> DRIZZLE
            in 61..67, in 80..82 -> RAIN
            in 71..77, 85, 86 -> SNOW
            in 95..99 -> THUNDERSTORM
            else -> CLOUDY
        }
    }

    @DrawableRes
    fun iconRes(isDay: Boolean): Int = when (this) {
        CLEAR -> if (isDay) R.drawable.ic_weather_clear_day else R.drawable.ic_weather_clear_night
        PARTLY_CLOUDY -> if (isDay) R.drawable.ic_weather_partly_day else R.drawable.ic_weather_partly_night
        CLOUDY -> R.drawable.ic_weather_cloudy
        FOG -> R.drawable.ic_weather_fog
        DRIZZLE, RAIN -> R.drawable.ic_weather_rain
        SNOW -> R.drawable.ic_weather_snow
        THUNDERSTORM -> R.drawable.ic_weather_thunderstorm
    }
}