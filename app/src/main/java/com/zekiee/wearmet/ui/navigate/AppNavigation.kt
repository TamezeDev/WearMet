package com.zekiee.wearmet.ui.navigate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.zekiee.wearmet.ui.screens.SearchScreen
import com.zekiee.wearmet.ui.screens.SettingsScreen
import com.zekiee.wearmet.ui.screens.SplashScreen
import com.zekiee.wearmet.ui.screens.WeatherScreen
import com.zekiee.wearmet.ui.theme.extendedColors
import com.zekiee.wearmet.ui.viewmodel.FavouritesViewModel
import com.zekiee.wearmet.ui.viewmodel.SettingsViewModel
import com.zekiee.wearmet.widget.updater.WidgetLocationResolver

@Composable
fun AppNavigation(widgetRequest: Int = 0) {
    val startupViewModel: StartupViewModel = viewModel()
    val plan by startupViewModel.plan.collectAsStateWithLifecycle()

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.extendedColors.backgroundBrush)
    ) {
        plan?.let { AppNavHost(it, widgetRequest) }
    }
}

@Composable
private fun AppNavHost(plan: StartupPlan, widgetRequest: Int) {
    val context = LocalContext.current
    val afterSplash: List<NavKey> = listOfNotNull(
        Search,
        plan.directLocation?.let { MainWeather(it) }
    )
    val initial: List<NavKey> = if (plan.splashMillis != null) listOf(Splash) else afterSplash
    val backStack = rememberNavBackStack(*initial.toTypedArray())

    LaunchedEffect(widgetRequest) {
        if (widgetRequest == 0) return@LaunchedEffect
        WidgetLocationResolver.resolve(context.applicationContext)?.let { location ->
            backStack.clear()
            backStack.add(Search)
            backStack.add(MainWeather(location))
        }
    }

    NavDisplay(
        backStack = backStack,
        entryProvider = entryProvider {
            entry<Splash> {
                SplashScreen(durationMillis = plan.splashMillis ?: 0L) {
                    if (backStack.lastOrNull() == Splash) {
                        backStack.clear()
                        backStack.addAll(afterSplash)
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
                    onOpenSettings = { backStack.add(Settings) }
                )
            }
            entry<Settings> {
                val settingsViewModel: SettingsViewModel = viewModel()
                val favouritesViewModel: FavouritesViewModel = viewModel()
                val settings by settingsViewModel.settings.collectAsStateWithLifecycle()
                val favourites by favouritesViewModel.favourites.collectAsStateWithLifecycle()

                SettingsScreen(
                    settings = settings,
                    favourites = favourites,
                    onBack = { backStack.removeLastOrNull() },
                    onSplashEnabledChange = settingsViewModel::setSplashEnabled,
                    onSplashDurationChange = settingsViewModel::setSplashDuration,
                    onAutoOpenChange = settingsViewModel::setAutoOpen,
                    onPreferredChange = settingsViewModel::setPreferred,
                    onUpdateIntervalChange = settingsViewModel::setUpdateInterval
                )
            }
        }
    )
}
