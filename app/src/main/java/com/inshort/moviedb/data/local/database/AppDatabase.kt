package com.inshort.moviedb.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.inshort.moviedb.data.local.dao.MovieDao
import com.inshort.moviedb.data.local.entity.MovieCategoryEntity
import com.inshort.moviedb.data.local.entity.MovieEntity

@Database(
    entities = [MovieEntity::class, MovieCategoryEntity::class ],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "movies_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
