package io.github.mexikoedi.smdf.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalMovie::class], version = 1, exportSchema = false)
abstract class LocalMovieDatabase : RoomDatabase() {
    abstract fun localMovieDao(): LocalMovieDao
}