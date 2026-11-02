package com.zekiee.wearmet.data.api

object ManageApiService {

    val geocodingApiService: GeocodingService by lazy {
        GeocodingApiService.retrofit.create(
            GeocodingService::class.java
        )
    }

}