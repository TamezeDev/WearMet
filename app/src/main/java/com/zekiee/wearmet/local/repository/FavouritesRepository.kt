package com.zekiee.wearmet.local.repository

import com.zekiee.wearmet.domain.model.Location
import com.zekiee.wearmet.local.dao.FavouriteDao
import com.zekiee.wearmet.local.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavouritesRepository(private val dao: FavouriteDao) {
    val favourites: Flow<List<Location>> = dao.observeAll().map { list ->
        list.map { it.toDomain() }
    }
    fun isFavourite(id: Int) = dao.observeIsFavourite(id)
    suspend fun add(location: Location) = dao.upsert(location.toEntity())
    suspend fun remove(id: Int) = dao.delete(id)
}

private fun FavouriteEntity.toDomain() =
    Location(id, name, latitude, longitude, countryCode, region)

private fun Location.toEntity() =
    FavouriteEntity(id, name, region, countryCode, latitude, longitude)