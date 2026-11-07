package com.zekiee.wearmet.local.data.room

import android.content.Context
import androidx.room3.Database
import androidx.room3.Room
import androidx.room3.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.zekiee.wearmet.local.dao.FavouriteDao
import com.zekiee.wearmet.local.entity.FavouriteEntity
import kotlinx.coroutines.Dispatchers

@Database(entities = [FavouriteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        fun get(context: Context): AppDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder<AppDatabase>(
                context.applicationContext, "wearmet.db"
            )
                .setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
                .build()
                .also { instance = it }
        }
    }
}

