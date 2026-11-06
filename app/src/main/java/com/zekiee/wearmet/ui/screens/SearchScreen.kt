package com.zekiee.wearmet.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zekiee.wearmet.R
import com.zekiee.wearmet.data.repository.DeviceLocationProvider
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
import kotlinx.coroutines.launch
import android.Manifest
import android.app.Activity
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority

@Composable
fun SearchScreen(
    onLocationSelected: (Location) -> Unit,
    viewModel: LocationViewModel = viewModel(),
    favouritesViewModel: FavouritesViewModel = viewModel()
) {
    val state = viewModel.uiState
    val favourites by favouritesViewModel.favourites.collectAsStateWithLifecycle()
    var searchText by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val provider = remember { DeviceLocationProvider(context) }
    val snackbarHostState = remember { SnackbarHostState() }
    val fallbackName = stringResource(R.string.my_location)
    val deniedMessage = stringResource(R.string.location_permission_denied)
    val unavailableMessage = stringResource(R.string.location_unavailable)

    fun locate() {
        scope.launch {
            val location = provider.currentLocation(fallbackName)
            if (location != null) onLocationSelected(location)
            else snackbarHostState.showSnackbar(unavailableMessage)
        }
    }

    val disabledMessage = stringResource(R.string.location_disabled)

    val settingsLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) locate()
        else scope.launch { snackbarHostState.showSnackbar(disabledMessage) }
    }

    fun checkSettingsAndLocate() {
        val request =
            LocationRequest.Builder(Priority.PRIORITY_BALANCED_POWER_ACCURACY, 10_000).build()
        val settings = LocationSettingsRequest.Builder().addLocationRequest(request).build()
        LocationServices.getSettingsClient(context)
            .checkLocationSettings(settings)
            .addOnSuccessListener { locate() }
            .addOnFailureListener { e ->
                if (e is ResolvableApiException) {
                    settingsLauncher.launch(IntentSenderRequest.Builder(e.resolution).build())
                } else {
                    scope.launch { snackbarHostState.showSnackbar(unavailableMessage) }
                }
            }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        if (result.values.any { it }) checkSettingsAndLocate()
        else scope.launch { snackbarHostState.showSnackbar(deniedMessage) }
    }
    Scaffold(
        containerColor = Color.Transparent,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
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
                LocationButton(onClick = {
                    permissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    )
                })
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