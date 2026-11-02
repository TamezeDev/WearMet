package com.zekiee.wearmet.ui.navigate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.zekiee.wearmet.ui.screens.SearchScreen
import com.zekiee.wearmet.ui.screens.SplashScreen
import com.zekiee.wearmet.ui.utils.CommonUtil

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(Search)

    Box(modifier = Modifier
        .fillMaxSize()
        .background(CommonUtil.backgroundGradient)){

        NavDisplay(
            backStack = backStack, entryProvider = entryProvider {
                entry<Splash> {
                    SplashScreen {
                        backStack.clear()
                        backStack.add(Search)
                    }
                }
                entry<Search> { SearchScreen() }
            }
        )
    }
}