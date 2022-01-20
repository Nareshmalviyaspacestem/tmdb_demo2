package com.movies.tmdb_demo2.hilt

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.movies.tmdb_demo2.networking.NetworkCalls
import com.movies.tmdb_demo2.repository.MovieDetailRepository
import com.movies.tmdb_demo2.room.MoviesDao
import com.movies.tmdb_demo2.utils.NetworkConnectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.movies.tmdb_demo2.room.MovieDatabase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


const val BASE_URL = "https://api.themoviedb.org/3/"

@Module
@InstallIn(SingletonComponent::class)
object HiltModules {


    @Provides
    fun provideMovieDetailRepository(networkCalls: NetworkCalls): MovieDetailRepository {
        return MovieDetailRepository(networkCalls)
    }


    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MoviesDao {
        return movieDatabase.getNewsDao()
    }

    @Provides
    fun provideNewsDatabase(@ApplicationContext context: Context): MovieDatabase {
        return MovieDatabase.get(context)
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(GsonConverterFactory.create(gson))
            client(okHttpClient)
        }.build()


    @Singleton
    @Provides
    fun provideNetworkCall(retrofit: Retrofit): NetworkCalls {
        return retrofit.create(NetworkCalls::class.java)
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        @ApplicationContext context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            interceptors().add(httpLoggingInterceptor)
            interceptors().add(NetworkConnectionInterceptor(context))
        }.build()


    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .serializeNulls()
            .setLenient()
            .create()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}