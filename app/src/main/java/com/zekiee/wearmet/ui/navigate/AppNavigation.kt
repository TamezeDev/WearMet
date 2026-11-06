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
import com.zekiee.wearmet.ui.state.LocationUi
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
                    SearchScreen(
                        onLocationSelected = { location ->
                            backStack.add(
                                MainWeather(
                                    name = location.name,
                                    region = location.region.orEmpty(),
                                    flag = location.flag.orEmpty(),
                                    latitude = location.latitude,
                                    longitude = location.longitude
                                )
                            )
                        }
                    )
                }
                entry<MainWeather> { key ->
                    WeatherScreen(
                        location = LocationUi(
                            name = key.name,
                            region = key.region,
                            flag = key.flag,
                            latitude = key.latitude,
                            longitude = key.longitude
                        ),
                        onSearch = { backStack.removeLastOrNull() }
                    )
                }
            }
        )
    }
}