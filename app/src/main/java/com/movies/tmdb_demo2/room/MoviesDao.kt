package com.movies.tmdb_demo2.room

import androidx.paging.PagingSource
import androidx.room.*
import com.movies.tmdb_demo2.model.Movie
import com.movies.tmdb_demo2.model.MovieRemoteKeys

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertList(listMovies: List<Movie>)

    @Query("DELETE FROM movies")
    suspend fun delete()

    @Query("SELECT * FROM movies")
    fun getAllMovies(): PagingSource<Int, Movie>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRemoteKeys(listRemoteKeys: List<MovieRemoteKeys>?)

    @Query("DELETE FROM remoteKeys")
    suspend fun deleteRemoteKeys()

    @Query("SELECT * FROM remoteKeys WHERE id=:id")
    suspend fun getAllKeys(id:String): MovieRemoteKeys?

}