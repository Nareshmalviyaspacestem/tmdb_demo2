package com.movies.tmdb_demo2.networking

import com.movies.tmdb_demo2.model.MovieDetail
import com.movies.tmdb_demo2.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface NetworkCalls {


    @GET("discover/movie")
    suspend fun getMovies(
        @Query("api_key") query: String,
        @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("movie/{movieID}")
    suspend fun getMovieDetail(
        @Path("movieID")  movieID :String,
        @Query("api_key") query: String
    ): Response<MovieDetail>


}