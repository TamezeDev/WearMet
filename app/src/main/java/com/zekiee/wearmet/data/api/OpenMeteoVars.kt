package com.zekiee.wearmet.data.api

object OpenMeteoVars {
    const val CURRENT = "temperature_2m,relative_humidity_2m,apparent_temperature,is_day," +
            "precipitation,weather_code,cloud_cover,pressure_msl,wind_speed_10m," +
            "wind_direction_10m,wind_gusts_10m"
    const val DAILY = "weather_code,temperature_2m_max,temperature_2m_min,sunrise,sunset," +
            "uv_index_max,precipitation_sum,precipitation_probability_max,wind_speed_10m_max"
    const val HOURLY = "temperature_2m,precipitation_probability,weather_code"
}