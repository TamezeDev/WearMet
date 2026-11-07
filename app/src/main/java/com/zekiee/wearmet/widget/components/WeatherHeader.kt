package com.zekiee.wearmet.widget.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.size
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.zekiee.wearmet.ui.theme.WidgetColors

@Composable
fun WeatherHeader(icon: Int, temperature: Int, compact: Boolean) {
    Row(
        modifier = GlanceModifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            provider = ImageProvider(icon),
            contentDescription = null,
            modifier = GlanceModifier.size(if (compact) 34.dp else 46.dp)
        )
        Spacer(GlanceModifier.width(6.dp))
        Text(
            text = "$temperature°",
            style = TextStyle(
                color = WidgetColors.primaryText,
                fontSize = if (compact) 28.sp else 38.sp,
                fontWeight = FontWeight.Bold
            )
        )
    }
}