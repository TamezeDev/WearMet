package com.zekiee.wearmet.ui.viewmodel

import com.zekiee.wearmet.widget.WearMetWidget
import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.glance.appwidget.updateAll
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.zekiee.wearmet.R
import com.zekiee.wearmet.data.repository.OpenMeteoRepository
import com.zekiee.wearmet.domain.model.Location
import com.zekiee.wearmet.domain.result.WeatherResult
import com.zekiee.wearmet.local.data.dataStore.LastLocationStore
import com.zekiee.wearmet.local.data.room.AppDatabase
import com.zekiee.wearmet.local.repository.FavouritesRepository
import com.zekiee.wearmet.ui.state.OpenMeteoState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WeatherViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = OpenMeteoRepository()
    private val favourites = FavouritesRepository(AppDatabase.get(app).favouriteDao())
    private var loadJob: Job? = null
    private val currentLocation = MutableStateFlow<Location?>(null)
    var uiState by mutableStateOf<OpenMeteoState>(OpenMeteoState.Loading)
        private set
    private val lastLocation = LastLocationStore(app)

    @OptIn(ExperimentalCoroutinesApi::class)
    val isFavourite: StateFlow<Boolean> = currentLocation
        .filterNotNull()
        .flatMapLatest { favourites.isFavourite(it.id) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    fun load(location: Location) {
        if (location == currentLocation.value && uiState is OpenMeteoState.Success) return
        currentLocation.value = location
        viewModelScope.launch {
            lastLocation.save(location)
            WearMetWidget().updateAll(getApplication())
        }
        refresh()
    }

    fun refresh() {
        val location = currentLocation.value ?: return
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

    fun toggleFavourite() {
        val location = currentLocation.value ?: return
        viewModelScope.launch {
            if (isFavourite.value) favourites.remove(location.id) else favourites.add(location)
        }
    }
}