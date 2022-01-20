package com.movies.tmdb_demo2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.movies.tmdb_demo2.networking.NetworkCalls
import com.movies.tmdb_demo2.paging.MoviePaging
import com.movies.tmdb_demo2.paging.MovieRemoteMediator
import com.movies.tmdb_demo2.room.MoviesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val networkCalls: NetworkCalls,
    private val moviesDao: MoviesDao
) : ViewModel() {

    @ExperimentalPagingApi
    val pager = Pager(
        PagingConfig(pageSize = 20),
        remoteMediator = MovieRemoteMediator(moviesDao, networkCalls, 1)
    ) {
        moviesDao.getAllMovies()
    }.flow


    //for paging without room
    val data = Pager(
        PagingConfig(pageSize = 20)
    ) {
        MoviePaging(networkCalls)
    }.liveData
        .cachedIn(viewModelScope)

}