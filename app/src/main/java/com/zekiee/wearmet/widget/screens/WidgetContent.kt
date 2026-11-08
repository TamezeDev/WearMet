package com.zekiee.wearmet.widget.screens

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.LocalContext
import androidx.glance.LocalSize
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.zekiee.wearmet.MainActivity
import com.zekiee.wearmet.ui.theme.WidgetColors
import com.zekiee.wearmet.widget.WearMetWidget
import com.zekiee.wearmet.widget.state.WidgetState

@Composable
fun WidgetContent(state: WidgetState) {
    val compact = LocalSize.current.height < WearMetWidget.REGULAR.height
    val context = LocalContext.current
    val openApp = Intent(context, MainActivity::class.java)
        .putExtra(MainActivity.EXTRA_OPEN_WIDGET_LOCATION, true)
    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(WidgetColors.background)
            .cornerRadius(24.dp)
            .padding(if (compact) 8.dp else 10.dp)
            .clickable(actionStartActivity(openApp)),
        contentAlignment = Alignment.Center
    ) {
        when (state) {
            is WidgetState.Message -> Text(
                text = state.text,
                style = TextStyle(
                    color = WidgetColors.primaryText,
                    textAlign = TextAlign.Center
                )
            )

            is WidgetState.Data -> WeatherLayout(state, compact)
        }
    }
}