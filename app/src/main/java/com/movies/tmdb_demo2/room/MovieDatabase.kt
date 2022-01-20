package com.movies.tmdb_demo2.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.movies.tmdb_demo2.model.Movie
import com.movies.tmdb_demo2.model.MovieRemoteKeys

@Database(entities = [Movie::class,MovieRemoteKeys::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    companion object {
        fun get(context: Context): MovieDatabase {
            return Room.databaseBuilder(context, MovieDatabase::class.java, "movies_db")
                .fallbackToDestructiveMigration().build()
        }
    }

    abstract fun getNewsDao(): MoviesDao

}