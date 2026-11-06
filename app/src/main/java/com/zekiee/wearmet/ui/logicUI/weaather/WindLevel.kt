package com.zekiee.wearmet.ui.logicUI.weaather

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import com.zekiee.wearmet.R

enum class WindLevel(@DrawableRes val arrowRes: Int) {
    LIGHT(R.drawable.ic_wind_arrow_light),
    MODERATE(R.drawable.ic_wind_arrow_moderate),
    STRONG(R.drawable.ic_wind_arrow_strong),
    VERY_STRONG(R.drawable.ic_wind_arrow_very_strong);

    companion object {
        fun fromSpeed(kmh: Double): WindLevel = when {
            kmh < 12 -> LIGHT
            kmh < 29 -> MODERATE
            kmh < 50 -> STRONG
            else -> VERY_STRONG
        }
    }
}

@Composable
fun WindArrow(speedKmh: Double, directionDegrees: Int, modifier: Modifier = Modifier) {
    Icon(
        painter = painterResource(WindLevel.fromSpeed(speedKmh).arrowRes),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onSurface,
        modifier = modifier.rotate(directionDegrees + 180f)
    )
}