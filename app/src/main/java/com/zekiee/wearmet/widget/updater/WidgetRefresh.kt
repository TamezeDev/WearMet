package com.zekiee.wearmet.widget.updater

import android.content.Context
import androidx.glance.appwidget.updateAll
import com.zekiee.wearmet.widget.WearMetWidget
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object WidgetRefresh {
    private val _tick = MutableStateFlow(0)
    val tick: StateFlow<Int> = _tick.asStateFlow()

    fun bump() {
        _tick.value++
    }

    suspend fun refreshAll(context: Context) {
        bump()
        WearMetWidget().updateAll(context)
    }
}