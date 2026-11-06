package com.zekiee.wearmet.ui.navigate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.zekiee.wearmet.ui.screens.SearchScreen
import com.zekiee.wearmet.ui.screens.SplashScreen
import com.zekiee.wearmet.ui.screens.WeatherScreen
import com.zekiee.wearmet.ui.theme.extendedColors

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(Splash)

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.extendedColors.backgroundBrush)
    ) {
        NavDisplay(
            backStack = backStack,
            entryProvider = entryProvider {
                entry<Splash> {
                    SplashScreen {
                        backStack.clear()
                        backStack.add(Search)
                    }
                }
                entry<Search> {
                    SearchScreen(onLocationSelected = { backStack.add(MainWeather(it)) })
                }
                entry<MainWeather> { key ->
                    WeatherScreen(
                        location = key.location,
                        onSearch = { backStack.removeLastOrNull() },
                        onOpenSettings = { }
                    )
                }
            }
        )
    }
}