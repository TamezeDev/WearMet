package com.zekiee.wearmet.data.api

object ManageApiService {
    val geocodingApiService: GeocodingApiService by lazy {
        ApiConnection.retrofit(ApiUrl.BASE_URL_GEOCODING).create(
            GeocodingApiService::class.java
        )
    }

    val openMeteoApiService: OpenMeteoApiService by lazy {
        ApiConnection.retrofit(ApiUrl.BASE_URL_OPEN_METEO).create(
            OpenMeteoApiService::class.java
        )
    }
}