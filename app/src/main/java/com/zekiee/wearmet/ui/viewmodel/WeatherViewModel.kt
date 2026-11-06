package com.zekiee.wearmet.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zekiee.wearmet.R
import com.zekiee.wearmet.data.repository.OpenMeteoRepository
import com.zekiee.wearmet.domain.result.WeatherResult
import com.zekiee.wearmet.ui.state.LocationUi
import com.zekiee.wearmet.ui.state.OpenMeteoState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val repository = OpenMeteoRepository()
    private var loadJob: Job? = null
    private var currentLocation: LocationUi? = null

    var uiState by mutableStateOf<OpenMeteoState>(OpenMeteoState.Loading)
        private set
    var isFavourite by mutableStateOf(false)
        private set

    fun load(location: LocationUi) {
        if (location == currentLocation && uiState is OpenMeteoState.Success) return
        currentLocation = location
        refresh()
    }

    fun refresh() {
        val location = currentLocation ?: return
        loadJob?.cancel()
        uiState = OpenMeteoState.Loading
        loadJob = viewModelScope.launch {
            uiState = when (val result =
                repository.getWeatherData(location.latitude, location.longitude)) {
                is WeatherResult.Success -> OpenMeteoState.Success(result.weather)
                WeatherResult.NetworkError -> OpenMeteoState.Error(R.string.error_network)
                WeatherResult.ServerError -> OpenMeteoState.Error(R.string.error_server)
                WeatherResult.UnknownError -> OpenMeteoState.Error(R.string.error_unknown)
            }
        }
    }

    fun toggleFavourite() { isFavourite = !isFavourite }
}