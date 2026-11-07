package com.zekiee.wearmet

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import com.zekiee.wearmet.ui.navigate.AppNavigation
import com.zekiee.wearmet.ui.theme.WearMetTheme

class MainActivity : ComponentActivity() {

    private var widgetRequest by mutableIntStateOf(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (savedInstanceState == null) registerWidgetRequest(intent)
        setContent {
            WearMetTheme {
                AppNavigation(widgetRequest = widgetRequest)
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        registerWidgetRequest(intent)
    }

    private fun registerWidgetRequest(intent: Intent) {
        if (intent.getBooleanExtra(EXTRA_OPEN_WIDGET_LOCATION, false)) widgetRequest++
    }

    companion object {
        const val EXTRA_OPEN_WIDGET_LOCATION = "open_widget_location"
    }
}