package com.zekiee.wearmet.ui.state

import androidx.annotation.StringRes
import com.zekiee.wearmet.domain.model.Location

sealed interface GeoCodingState {
    data object Waiting : GeoCodingState
    data object Loading : GeoCodingState
    data class Success(val locations: List<Location>) : GeoCodingState
    data class Error(@StringRes val messageRes: Int) : GeoCodingState
}