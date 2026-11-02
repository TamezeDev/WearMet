package com.zekiee.wearmet.data.response

import com.zekiee.wearmet.data.dto.GeocodingDto

data class GeocodingResponse(
    val results: List<GeocodingDto>?
)