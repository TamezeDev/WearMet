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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class)
class LocationViewModel : ViewModel() {
    private val repository = LocationRepository()
    private val textRequest = MutableStateFlow("")
    var uiState by mutableStateOf<GeoCodingState>(GeoCodingState.Waiting)

    init {
        viewModelScope.launch {
            textRequest
                .debounce(400.milliseconds)
                .map { it.trim() }
                .distinctUntilChanged()
                .collectLatest {
                    if (it.length < 3) uiState = GeoCodingState.Waiting
                    else getLocations(it)
                }
        }
    }

    fun onTextChange(text: String) {
        textRequest.value = text
    }

    fun getLocations(searchText: String) {
        uiState = GeoCodingState.Loading
        viewModelScope.launch {

            uiState = when (val result = repository.getLocations(searchText)) {
                is LocationResult.Success -> {
                    if (result.locations.isEmpty()) GeoCodingState.Error(R.string.no_results)
                    else GeoCodingState.Success(result.locations)
                }

                LocationResult.NetworkError -> GeoCodingState.Error(R.string.error_network)

                LocationResult.ServerError -> GeoCodingState.Error(R.string.error_server)
                LocationResult.UnknownError -> GeoCodingState.Error(R.string.error_unknown)
            }
        }
    }

}
