package com.zekiee.wearmet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.zekiee.wearmet.ui.navigate.AppNavigation
import com.zekiee.wearmet.ui.theme.WearMetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WearMetTheme {
                AppNavigation()
            }
        }
    }
}

