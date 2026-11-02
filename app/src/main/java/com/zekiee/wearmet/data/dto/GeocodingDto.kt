package com.zekiee.wearmet.data.dto

import com.google.gson.annotations.SerializedName
import com.zekiee.wearmet.domain.model.Location

data class GeocodingDto(
    val id: Int?,
    val name: String?,
    val latitude: Double?,
    val longitude: Double?,
    @SerializedName("country_code") val countryCode: String?,
    val country: String?,
    @SerializedName("admin1") val region: String?,
    val population: Int?
)

fun GeocodingDto.toDomain(): Location? {
    val nameLocation = name ?: return null
    val latitudeLocation = latitude ?: return null
    val longitudeLocation = longitude ?: return null

    return Location(
        id,
        nameLocation,
        latitudeLocation,
        longitudeLocation,
        countryCode,
        country,
        region,
        population
    )

}