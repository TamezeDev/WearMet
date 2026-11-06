package com.zekiee.wearmet.ui.navigate

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object Splash : NavKey

@Serializable
data object Search : NavKey

@Serializable
data class MainWeather(
    val name: String,
    val region: String,
    val flag: String,
    val latitude: Double,
    val longitude: Double
) : NavKey