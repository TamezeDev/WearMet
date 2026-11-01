package com.zekiee.wearmet.ui.navigate

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.zekiee.wearmet.ui.screens.MainScreen
import com.zekiee.wearmet.ui.screens.SplashScreen

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(Splash)

    NavDisplay(
        backStack = backStack, entryProvider = entryProvider {
            entry<Splash> {
                SplashScreen {
                    backStack.clear()
                    backStack.add(Search)
                }
            }
            entry<Search> { MainScreen() }
        }
    )
}