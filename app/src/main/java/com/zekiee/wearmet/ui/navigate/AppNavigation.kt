package com.zekiee.wearmet.ui.navigate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.zekiee.wearmet.local.data.dataStore.LastLocationStore
import com.zekiee.wearmet.ui.screens.SearchScreen
import com.zekiee.wearmet.ui.screens.SplashScreen
import com.zekiee.wearmet.ui.screens.WeatherScreen
import com.zekiee.wearmet.ui.theme.extendedColors

@Composable
fun AppNavigation(widgetRequest: Int = 0) {
    val backStack = rememberNavBackStack(Splash)
    val context = LocalContext.current

    LaunchedEffect(widgetRequest) {
        if (widgetRequest == 0) return@LaunchedEffect
        LastLocationStore(context.applicationContext).get()?.let { location ->
            backStack.clear()
            backStack.add(Search)
            backStack.add(MainWeather(location))
        }
    }
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
                        if (backStack.lastOrNull() == Splash) {
                            backStack.clear()
                            backStack.add(Search)
                        }
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