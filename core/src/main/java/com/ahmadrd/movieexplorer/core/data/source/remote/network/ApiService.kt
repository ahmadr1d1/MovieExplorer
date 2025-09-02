package com.ahmadrd.movieexplorer.core.data.source.remote.network

import com.ahmadrd.movieexplorer.core.data.source.remote.response.GenresMovieResponse
import com.ahmadrd.movieexplorer.core.data.source.remote.response.PopularMoviesResponse
import com.ahmadrd.movieexplorer.core.data.source.remote.response.TrendingMoviesResponse
import retrofit2.http.*

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): PopularMoviesResponse

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("time_window") timeWindow: String = "day"
    ): TrendingMoviesResponse

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresMovieResponse
}