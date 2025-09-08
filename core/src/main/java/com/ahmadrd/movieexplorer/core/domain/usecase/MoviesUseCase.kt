package com.ahmadrd.movieexplorer.core.domain.usecase

import com.ahmadrd.movieexplorer.core.data.Resource
import com.ahmadrd.movieexplorer.core.domain.model.CastingMovie
import com.ahmadrd.movieexplorer.core.domain.model.DetailMovie
import com.ahmadrd.movieexplorer.core.domain.model.GenresMovie
import com.ahmadrd.movieexplorer.core.domain.model.PopularMovies
import com.ahmadrd.movieexplorer.core.domain.model.SimilarMovies
import com.ahmadrd.movieexplorer.core.domain.model.TrendingMovies
import kotlinx.coroutines.flow.Flow

interface MoviesUseCase {

    // Popular Movies
    fun getPopularMovies(): Flow<Resource<List<PopularMovies>>>
    fun getFavoritePopularMovies(): Flow<List<PopularMovies>>
    fun setFavoriteMovies(popularMovies: PopularMovies, state: Boolean)

    // Trending Movies (day)
    fun getTrendingMovies(): Flow<Resource<List<TrendingMovies>>>
    fun getFavoriteTrendingMovies(): Flow<List<TrendingMovies>>
    fun setFavoriteTrendingMovies(trendingMovies: TrendingMovies, state: Boolean)

    // Genres Movie
    fun getGenresMovie(): Flow<Resource<List<GenresMovie>>>

    // Detail Movie
    fun getDetailMovie(movieId: Int): Flow<Resource<DetailMovie>>

    // Casting Movie
    fun getCastingMovie(movieId: Int): Flow<Resource<List<CastingMovie>>>

    // Similar Movie
    fun getSimilarMovies(movieId: Int): Flow<Resource<List<SimilarMovies>>>

}