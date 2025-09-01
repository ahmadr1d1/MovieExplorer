package com.ahmadrd.movieexplorer.core.domain.usecase

import com.ahmadrd.movieexplorer.core.data.Resource
import com.ahmadrd.movieexplorer.core.domain.model.PopularMovies
import com.ahmadrd.movieexplorer.core.domain.model.TrendingMovies
import kotlinx.coroutines.flow.Flow

interface MoviesUseCase {

    // Popular Movies
    fun getPopularMovies(): Flow<Resource<List<PopularMovies>>>
    fun getFavoritePopularMovies(): Flow<List<PopularMovies>>
    fun setFavoriteMovies(popularMovies: PopularMovies, state: Boolean)

    // Trending All
    fun getTrendingMovies(): Flow<Resource<List<TrendingMovies>>>
    fun getFavoriteTrendingMovies(): Flow<List<TrendingMovies>>
    fun setFavoriteTrendingMovies(trendingMovies: TrendingMovies, state: Boolean)


}