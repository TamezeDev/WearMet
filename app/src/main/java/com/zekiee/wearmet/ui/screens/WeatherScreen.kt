package com.zekiee.wearmet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zekiee.wearmet.ui.components.weatherScreen.ErrorView
import com.zekiee.wearmet.ui.components.weatherScreen.LoadingView
import com.zekiee.wearmet.ui.components.weatherScreen.WeatherContent
import com.zekiee.wearmet.ui.components.weatherScreen.topbar.WeatherTopBar
import com.zekiee.wearmet.ui.state.LocationUi
import com.zekiee.wearmet.ui.state.OpenMeteoState
import com.zekiee.wearmet.ui.theme.extendedColors
import com.zekiee.wearmet.ui.viewmodel.WeatherViewModel


@Composable
fun WeatherScreen(
    location: LocationUi,
    onSearch: () -> Unit,
    viewModel: WeatherViewModel = viewModel()
) {
    LaunchedEffect(location) { viewModel.load(location) }

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.extendedColors.backgroundBrush)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                WeatherTopBar(
                    locationName = location.name,
                    locationRegion = location.region,
                    locationFlag = location.flag,
                    isFavourite = viewModel.isFavourite,
                    onRefresh = viewModel::refresh,
                    onToggleFavourite = viewModel::toggleFavourite,
                    onOpenFavourites = {},
                    onOpenSettings = {},
                    onSearch = onSearch
                )
            }
        ) { padding ->
            val modifier = Modifier.padding(padding)
            when (val state = viewModel.uiState) {
                OpenMeteoState.Loading -> LoadingView(modifier)
                is OpenMeteoState.Error -> ErrorView(
                    message = stringResource(state.messageRes),
                    onRetry = viewModel::refresh,
                    modifier = modifier
                )
                is OpenMeteoState.Success -> WeatherContent(state.weather, modifier)
            }
        }
    }
}
