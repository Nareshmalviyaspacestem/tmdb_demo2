package com.movies.tmdb_demo2.paging

import com.movies.tmdb_demo2.model.Movie


interface ClickHandler {

    fun handleSmallClick(item: Movie)
}
