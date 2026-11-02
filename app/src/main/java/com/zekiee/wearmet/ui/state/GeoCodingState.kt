package com.zekiee.wearmet.ui.state

import com.zekiee.wearmet.domain.model.Location

sealed interface GeoCodingState {
    data object Loading : GeoCodingState
    data class Success(val locations: List<Location>) : GeoCodingState
    data class Error(val message: String) : GeoCodingState
}