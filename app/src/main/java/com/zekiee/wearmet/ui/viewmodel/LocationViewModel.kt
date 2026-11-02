package com.zekiee.wearmet.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.zekiee.wearmet.data.repository.LocationRepository
import com.zekiee.wearmet.ui.state.GeoCodingState
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zekiee.wearmet.R
import com.zekiee.wearmet.domain.result.LocationResult

class LocationViewModel : ViewModel() {
    private val repository = LocationRepository()
    var uiState by mutableStateOf<GeoCodingState>(GeoCodingState.Loading)
    var errorMessage by mutableStateOf<String?>(null)

    fun getLocations(searchText: String) {
        uiState = GeoCodingState.Loading
        viewModelScope.launch {

            when (val result = repository.getLocations(searchText)) {
                is LocationResult.Success -> {
                    if (result.locations.isEmpty()) errorMessage = R.string.no_results.toString()
                    else uiState = GeoCodingState.Success(result.locations)
                }

                LocationResult.NetworkError -> errorMessage = R.string.error_network.toString()
                LocationResult.ServerError -> errorMessage = R.string.error_server.toString()
                LocationResult.UnknownError -> errorMessage = R.string.error_unknown.toString()
            }
        }
    }

}
