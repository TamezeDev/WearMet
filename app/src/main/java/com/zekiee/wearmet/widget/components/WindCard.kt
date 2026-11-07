package com.zekiee.wearmet.widget.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.zekiee.wearmet.R
import com.zekiee.wearmet.domain.model.CurrentWeather
import com.zekiee.wearmet.ui.theme.WidgetColors
import kotlin.math.roundToInt

@Composable
fun WindCard(current: CurrentWeather, compact: Boolean) {
    val context = LocalContext.current
    val unit = context.getString(R.string.unit_kmh)
    val valueSize = if (compact) 11.sp else 13.sp
    val labelSize = if (compact) 9.sp else 10.sp

    Row(
        modifier = GlanceModifier
            .fillMaxWidth()
            .background(WidgetColors.cardBackground)
            .cornerRadius(14.dp)
            .padding(horizontal = 4.dp, vertical = if (compact) 3.dp else 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = windArrow(current.windDirection),
            maxLines = 1,
            style = TextStyle(
                color = WidgetColors.cardText,
                fontSize = if (compact) 16.sp else 20.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = GlanceModifier.padding(horizontal = 4.dp)
        )
        WidgetDivider()
        WindColumn(
            label = context.getString(R.string.detail_wind),
            value = "${current.windSpeed.roundToInt()} $unit",
            labelSize = labelSize,
            valueSize = valueSize,
            modifier = GlanceModifier.defaultWeight()
        )
        WidgetDivider()
        WindColumn(
            label = context.getString(R.string.detail_gusts),
            value = "${current.windGusts.roundToInt()} $unit",
            labelSize = labelSize,
            valueSize = valueSize,
            modifier = GlanceModifier.defaultWeight()
        )
    }
}

@Composable
private fun WindColumn(
    label: String,
    value: String,
    labelSize: androidx.compose.ui.unit.TextUnit,
    valueSize: androidx.compose.ui.unit.TextUnit,
    modifier: GlanceModifier
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = label,
            maxLines = 1,
            style = TextStyle(color = WidgetColors.cardText, fontSize = labelSize)
        )
        Text(
            text = value,
            maxLines = 1,
            style = TextStyle(
                color = WidgetColors.cardText,
                fontSize = valueSize,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

private fun windArrow(degrees: Int): String {
    val arrows = arrayOf("↑", "↗", "→", "↘", "↓", "↙", "←", "↖")
    return arrows[((((degrees + 180) % 360) + 22.5) / 45).toInt() % 8]
}