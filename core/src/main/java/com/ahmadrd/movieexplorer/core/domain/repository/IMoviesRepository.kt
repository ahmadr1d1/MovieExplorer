package com.ahmadrd.movieexplorer.core.domain.repository

import com.ahmadrd.movieexplorer.core.data.Resource
import com.ahmadrd.movieexplorer.core.domain.model.AllMovie
import com.ahmadrd.movieexplorer.core.domain.model.CastingMovie
import com.ahmadrd.movieexplorer.core.domain.model.DetailMovie
import com.ahmadrd.movieexplorer.core.domain.model.GenresMovie
import com.ahmadrd.movieexplorer.core.domain.model.PopularMovies
import com.ahmadrd.movieexplorer.core.domain.model.SimilarMovies
import com.ahmadrd.movieexplorer.core.domain.model.TrendingMovies
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {

    // Popular Movies
    fun getPopularMovies(): Flow<Resource<List<PopularMovies>>>

    // Trending All
    fun getTrendingMovies(): Flow<Resource<List<TrendingMovies>>>

    // Genres Movie
    fun getGenresMovie(): Flow<Resource<List<GenresMovie>>>

    // Detail Movie
    fun getDetailMovie(movieId: Int): Flow<Resource<DetailMovie>>

    // Casting Movie
    fun getCastingMovie(movieId: Int): Flow<Resource<List<CastingMovie>>>

    // Similar Movie
    fun getSimilarMovies(movieId: Int): Flow<Resource<List<SimilarMovies>>>

    // Favorite Movie
    fun getFavorites(): Flow<List<AllMovie>>
    suspend fun setFavorite(allMovie: AllMovie, favorite: Boolean)
    suspend fun removeFavorite(movie: AllMovie)
    fun isFavorite(movieId: Int): Flow<Boolean>

}