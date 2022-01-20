package com.movies.tmdb_demo2.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.movies.tmdb_demo2.model.Movie
import com.movies.tmdb_demo2.networking.NetworkCalls
import com.movies.tmdb_demo2.utils.Constants.Companion.apiQuery

class MoviePaging(val networkCalls: NetworkCalls) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1

        return try {

            val data = networkCalls.getMovies(apiQuery, page)

            LoadResult.Page(data = data.body()?.items!!,
                prevKey = if (page==1)null else page-1,
                nextKey = if (data.body()?.items==null) null else page+1)

        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}