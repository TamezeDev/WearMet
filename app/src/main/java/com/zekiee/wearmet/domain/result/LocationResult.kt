package com.zekiee.wearmet.domain.result

import com.zekiee.wearmet.domain.model.Location

sealed class LocationResult {
    data class Success(val locations: List<Location>) : LocationResult()
    data object NetworkError : LocationResult()
    data object ServerError : LocationResult()
    data object UnknownError : LocationResult()
}