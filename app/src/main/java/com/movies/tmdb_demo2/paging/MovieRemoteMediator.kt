package com.movies.tmdb_demo2.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.movies.tmdb_demo2.model.Movie
import com.movies.tmdb_demo2.model.MovieRemoteKeys
import com.movies.tmdb_demo2.networking.NetworkCalls
import com.movies.tmdb_demo2.room.MoviesDao
import com.movies.tmdb_demo2.utils.Constants.Companion.apiQuery
import java.io.InvalidObjectException

@ExperimentalPagingApi
class MovieRemoteMediator(
    private val moviesDao: MoviesDao,
    private val networkCalls: NetworkCalls,
    private var initialPage: Int = 1
) :
    RemoteMediator<Int, Movie>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {

        try {
            val page = when (loadType) {
                LoadType.APPEND -> {

                    val remoteKey =
                        getLastKey(state) ?: throw InvalidObjectException("remote key not valid!!")
                    remoteKey.next ?: return MediatorResult.Success(endOfPaginationReached = true)


                }
                LoadType.PREPEND -> {

                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.REFRESH -> {

                    val remoteKey = getClosestKey(state)
                    remoteKey?.next?.minus(1) ?: initialPage
                }
            }

            val response = networkCalls.getMovies(apiQuery, page)
            val endOfPagination = response.body()?.items?.size!! < state.config.pageSize

            if (response.isSuccessful) {

                response.body()?.let {
                    if (loadType == LoadType.REFRESH) {
                        moviesDao.delete()
                        moviesDao.deleteRemoteKeys()
                    }

                    val prevKey = if (page == initialPage) null else page - 1
                    val nextKey = if (endOfPagination) null else page + 1

                    val list = response.body()?.items?.map { movie ->
                        MovieRemoteKeys(movie.id.toString(), prevKey, nextKey)
                    }

                    moviesDao.insertRemoteKeys(list)
                    moviesDao.insertList(it.items)

                }

                return MediatorResult.Success(endOfPagination)
            } else {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }

    }

   private suspend fun getClosestKey(state: PagingState<Int, Movie>): MovieRemoteKeys? {
        return state.anchorPosition?.let {
            state.closestItemToPosition(it)?.let { movie ->
                moviesDao.getAllKeys(movie.id.toString())
            }
        }
    }

  private  suspend fun getLastKey(state: PagingState<Int, Movie>): MovieRemoteKeys? {
        return state.lastItemOrNull()?.let {
            moviesDao.getAllKeys(it.id.toString())
        }
    }

}
