package com.movies.tmdb_demo2.model

import com.google.gson.annotations.SerializedName

data class MovieDetail (
    @field:SerializedName("original_title") val original_title: String,
    @field:SerializedName("overview") val overview: String,
    @field:SerializedName("poster_path") val poster_path: String?,
    @field:SerializedName("backdrop_path") val backdrop_path: String,
    @field:SerializedName("vote_count") val vote_count: Int
)