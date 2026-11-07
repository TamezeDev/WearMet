package com.zekiee.wearmet.widget.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.zekiee.wearmet.ui.logicUI.weaather.WeatherCondition
import com.zekiee.wearmet.ui.theme.WidgetColors
import com.zekiee.wearmet.widget.components.WeatherHeader
import com.zekiee.wearmet.widget.components.WindCard
import com.zekiee.wearmet.widget.state.WidgetState
import kotlin.math.roundToInt

@Composable
fun WeatherLayout(state: WidgetState.Data, compact: Boolean) {
    val current = state.weather.current
    val today = state.weather.daily.firstOrNull()
    val icon = WeatherCondition.fromCode(current.weatherCode).iconRes(current.isDay)

    Column(
        modifier = GlanceModifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = state.name,
            maxLines = 1,
            style = TextStyle(
                color = WidgetColors.secondaryText,
                fontSize = if (compact) 12.sp else 13.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        )
        WeatherHeader(icon, current.temperature.roundToInt(), compact)
        if (!compact && today != null) {
            Text(
                text = "↑ ${today.temperatureMax.roundToInt()}°   ↓ ${today.temperatureMin.roundToInt()}°",
                style = TextStyle(color = WidgetColors.secondaryText, fontSize = 12.sp)
            )
        }
        Spacer(GlanceModifier.height(if (compact) 4.dp else 8.dp))
        WindCard(current, compact)
    }
}