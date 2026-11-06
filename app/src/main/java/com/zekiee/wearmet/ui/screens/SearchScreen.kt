package com.zekiee.wearmet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zekiee.wearmet.domain.model.Location
import com.zekiee.wearmet.ui.components.searchScreen.ErrorText
import com.zekiee.wearmet.ui.components.searchScreen.FavouritesSection
import com.zekiee.wearmet.ui.components.searchScreen.LoadingItem
import com.zekiee.wearmet.ui.components.searchScreen.LocationButton
import com.zekiee.wearmet.ui.components.searchScreen.LocationList
import com.zekiee.wearmet.ui.components.searchScreen.SearchTextField
import com.zekiee.wearmet.ui.state.GeoCodingState
import com.zekiee.wearmet.ui.theme.WearMetTheme
import com.zekiee.wearmet.ui.theme.extendedColors
import com.zekiee.wearmet.ui.viewmodel.FavouritesViewModel
import com.zekiee.wearmet.ui.viewmodel.LocationViewModel

@Composable
fun SearchScreen(
    onLocationSelected: (Location) -> Unit,
    viewModel: LocationViewModel = viewModel(),
    favouritesViewModel: FavouritesViewModel = viewModel()
) {
    val state = viewModel.uiState
    val favourites by favouritesViewModel.favourites.collectAsStateWithLifecycle()
    var searchText by rememberSaveable { mutableStateOf("") }

    Scaffold(containerColor = Color.Transparent) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.extendedColors.backgroundBrush),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LocationButton()
                Spacer(modifier = Modifier.padding(12.dp))
                SearchTextField(searchText) {
                    searchText = it
                    viewModel.onTextChange(it)
                }
                Spacer(modifier = Modifier.padding(12.dp))

                when (state) {
                    is GeoCodingState.Waiting -> if (favourites.isNotEmpty()) {
                        FavouritesSection(favourites, onLocationSelected)
                    }
                    GeoCodingState.Loading -> LoadingItem()
                    is GeoCodingState.Error -> ErrorText(state)
                    is GeoCodingState.Success -> LocationList(state.locations, onLocationSelected)
                }
            }
        }
    }
}
@Preview
@Composable
fun ShowSearchScreen() {
    WearMetTheme { SearchScreen(onLocationSelected = {}) }
}