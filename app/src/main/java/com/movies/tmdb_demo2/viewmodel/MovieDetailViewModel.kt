package com.movies.tmdb_demo2.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.movies.tmdb_demo2.model.MovieDetail
import com.movies.tmdb_demo2.repository.MovieDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val  movieDetailRepository: MovieDetailRepository):ViewModel(){

   private  var movieDetail : MutableLiveData<MovieDetail> = MutableLiveData()

    suspend fun getMovieDetail( movieID:String,apiKey:String){

        movieDetailRepository.getMovieDetail(movieID,apiKey,movieDetail)

    }

    fun getMovieDetailData():MutableLiveData<MovieDetail>{
        return movieDetail
    }



}