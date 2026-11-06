package com.zekiee.wearmet.local.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import com.zekiee.wearmet.local.entity.FavouriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Query("SELECT * FROM favourites ORDER BY addedAt DESC")
    fun observeAll(): Flow<List<FavouriteEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favourites WHERE id = :id)")
    fun observeIsFavourite(id: Int): Flow<Boolean>

    @Upsert
    suspend fun upsert(favourite: FavouriteEntity)

    @Query("DELETE FROM favourites WHERE id = :id")
    suspend fun delete(id: Int)
}