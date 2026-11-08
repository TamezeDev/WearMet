package com.zekiee.wearmet.widget.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.ColorFilter
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.zekiee.wearmet.R
import com.zekiee.wearmet.domain.model.CurrentWeather
import com.zekiee.wearmet.ui.theme.WidgetColors
import kotlin.math.roundToInt

@Composable
fun WindInfo(current: CurrentWeather, compact: Boolean) {
    val speed = current.windSpeed.roundToInt()
    val gusts = current.windGusts.roundToInt()
    val values = if (gusts > speed) "$speed | $gusts" else "$speed"

    Row(
        modifier = GlanceModifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            provider = ImageProvider(R.drawable.ic_wind),
            contentDescription = null,
            modifier = GlanceModifier.size(if (compact) 18.dp else 24.dp),
            colorFilter = ColorFilter.tint(WidgetColors.primaryText)
        )


        Spacer(GlanceModifier.width(10.dp))
        Text(
            text = values,
            maxLines = 1,
            style = TextStyle(
                color = WidgetColors.primaryText,
                fontSize = if (compact) 14.sp else 16.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(GlanceModifier.width(5.dp))
        Text(
            modifier = GlanceModifier.padding(bottom = 8.dp),
            text = windArrow(current.windDirection),
            maxLines = 1,
            style = TextStyle(
                color = WidgetColors.primaryText,
                fontSize = if (compact) 25.sp else 28.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

private fun windArrow(degrees: Int): String {
    val arrows = arrayOf("↑", "↗", "→", "↘", "↓", "↙", "←", "↖")
    return arrows[((((degrees + 180) % 360) + 22.5) / 45).toInt() % 8]
}