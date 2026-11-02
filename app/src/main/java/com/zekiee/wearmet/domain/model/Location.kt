package com.zekiee.wearmet.domain.model

data class Location(
    val id: Int?,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val countryCode: String?,
    val country: String?,
    val region: String?,
    val population: Int?
)
