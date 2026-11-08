package com.zekiee.wearmet.local.data.dataStore

import com.zekiee.wearmet.domain.model.Location

enum class SplashDuration(val millis: Long) {
    SHORT(1_000),
    NORMAL(2_000)
}

enum class UpdateInterval(val hours: Long) {
    H1(1), H3(3), H6(6), H12(12)
}

data class AppSettings(
    val splashEnabled: Boolean = true,
    val splashDuration: SplashDuration = SplashDuration.NORMAL,
    val autoOpenPreferred: Boolean = false,
    val preferredLocationId: Int? = null,
    val preferredLocation: Location? = null,
    val updateInterval: UpdateInterval = UpdateInterval.H3
)