package com.zekiee.wearmet.ui.navigate

import androidx.navigation3.runtime.NavKey
import com.zekiee.wearmet.domain.model.Location
import kotlinx.serialization.Serializable

@Serializable
data object Splash : NavKey

@Serializable
data object Search : NavKey

@Serializable
data class MainWeather(val location: Location) : NavKey

@Serializable
data object Settings : NavKey