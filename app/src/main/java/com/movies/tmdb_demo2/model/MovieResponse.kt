package com.movies.tmdb_demo2.model


import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page") val page: Int = 0,
    @SerializedName("total_pages") val total_pages:Int,
    @SerializedName("results") val items: List<Movie> = emptyList()
)
