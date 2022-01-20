package com.movies.tmdb_demo2.repository

import androidx.lifecycle.MutableLiveData
import com.movies.tmdb_demo2.model.MovieDetail
import com.movies.tmdb_demo2.networking.NetworkCalls
import javax.inject.Inject

class MovieDetailRepository@Inject constructor(val networkCalls: NetworkCalls) {
    suspend fun getMovieDetail( movieID:String,apiKey:String,movieDetail :MutableLiveData<MovieDetail>){

        try {
            val response = networkCalls.getMovieDetail(movieID,apiKey)

            if (response.isSuccessful){
                movieDetail.postValue(response.body())
            }


        }catch (e:Exception){

        }

    }

}