package com.zekiee.wearmet.local.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "favourites")
data class FavouriteEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val region: String?,
    val countryCode: String,
    val latitude: Double,
    val longitude: Double,
    val addedAt: Long = System.currentTimeMillis()
)