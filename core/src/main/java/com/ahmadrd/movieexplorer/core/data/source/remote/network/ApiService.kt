package com.ahmadrd.movieexplorer.core.data.source.remote.network

import com.ahmadrd.movieexplorer.core.data.source.remote.response.PopularMoviesResponse
import retrofit2.http.*

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): PopularMoviesResponse
}