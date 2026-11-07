package com.zekiee.wearmet.widget

import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.zekiee.wearmet.widget.state.WidgetStateLoader

import android.content.Context
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import com.zekiee.wearmet.widget.screens.WidgetContent

class WearMetWidget : GlanceAppWidget() {

    override val sizeMode = SizeMode.Responsive(setOf(COMPACT, REGULAR))

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val state = WidgetStateLoader(context).load()
        provideContent { WidgetContent(state) }
    }

    companion object {
        val COMPACT = DpSize(110.dp, 110.dp)
        val REGULAR = DpSize(110.dp, 150.dp)
    }
}

class WearMetWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = WearMetWidget()
}