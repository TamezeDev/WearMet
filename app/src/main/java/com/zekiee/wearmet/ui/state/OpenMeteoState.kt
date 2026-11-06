package com.zekiee.wearmet.ui.state

import androidx.annotation.StringRes
import com.zekiee.wearmet.domain.model.Weather

sealed interface OpenMeteoState {
    data object Loading : OpenMeteoState
    data class Success(val weather: Weather) : OpenMeteoState
    data class Error(@StringRes val messageRes: Int) : OpenMeteoState
}
