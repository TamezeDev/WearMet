package com.zekiee.wearmet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zekiee.wearmet.domain.model.Location
import com.zekiee.wearmet.ui.components.weatherScreen.ErrorView
import com.zekiee.wearmet.ui.components.weatherScreen.LoadingView
import com.zekiee.wearmet.ui.components.weatherScreen.WeatherContent
import com.zekiee.wearmet.ui.components.weatherScreen.topbar.WeatherTopBar
import com.zekiee.wearmet.ui.state.OpenMeteoState
import com.zekiee.wearmet.ui.theme.extendedColors
import com.zekiee.wearmet.ui.viewmodel.WeatherViewModel


@Composable
fun WeatherScreen(
    location: Location,
    onSearch: () -> Unit,
    onOpenSettings: () -> Unit,
    viewModel: WeatherViewModel = viewModel()
) {
    LaunchedEffect(location) { viewModel.load(location) }
    val isFavourite by viewModel.isFavourite.collectAsStateWithLifecycle()

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
                    locationRegion = location.region.orEmpty(),
                    locationFlag = location.flag.orEmpty(),
                    isFavourite = isFavourite,
                    onToggleFavourite = viewModel::toggleFavourite,
                    onOpenSettings = onOpenSettings,
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