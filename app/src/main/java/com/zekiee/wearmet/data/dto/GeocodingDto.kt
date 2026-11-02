package com.zekiee.wearmet.data.dto

import com.google.gson.annotations.SerializedName
import com.zekiee.wearmet.domain.model.Location

data class GeocodingDto(
    val id: Int?,
    val name: String?,
    val latitude: Double?,
    val longitude: Double?,
    @SerializedName("country_code") val countryCode: String?,
    @SerializedName("admin1") val region: String?,
)

fun GeocodingDto.toDomain(): Location? {
    val idLocation = id ?: return null
    val nameLocation = name ?: return null
    val latitudeLocation = latitude ?: return null
    val longitudeLocation = longitude ?: return null
    val countryCodeLoc = countryCode ?: return null

    return Location(
        idLocation,
        nameLocation,
        latitudeLocation,
        longitudeLocation,
        countryCodeLoc,
        region)

}