package com.ahmadrd.movieexplorer.core.data.source.remote.network

import com.ahmadrd.movieexplorer.core.data.source.remote.response.*
import retrofit2.http.*

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): PopularMoviesResponse

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(): TrendingMoviesResponse

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresMovieResponse

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(@Path("movie_id") movieId: Int): DetailMovieResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getCastingMovie(@Path("movie_id") movieId: Int): CastingMovieResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(@Path("movie_id") movieId: Int): SimilarMovieResponse
}