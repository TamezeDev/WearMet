package com.zekiee.wearmet.widget.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.background
import androidx.glance.layout.Spacer
import androidx.glance.layout.height
import androidx.glance.layout.width
import com.zekiee.wearmet.ui.theme.WidgetColors

@Composable
fun WidgetDivider(height: Dp = 22.dp) {
    Spacer(
        GlanceModifier
            .width(1.dp)
            .height(height)
            .background(WidgetColors.cardText)
    )
}