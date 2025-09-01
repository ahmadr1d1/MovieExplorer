package com.ahmadrd.movieexplorer.core.data.source.local

import com.ahmadrd.movieexplorer.core.data.source.local.entity.PopularMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.TrendingMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.local.room.PopularMoviesDao
import com.ahmadrd.movieexplorer.core.data.source.local.room.TrendingMoviesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val popularMoviesDao: PopularMoviesDao,
    private val trendingMoviesDao: TrendingMoviesDao
) {

    // Popular Movies
    fun getPopularMovies(): Flow<List<PopularMoviesEntity>>
    = popularMoviesDao.getPopularMovies()

    fun getFavoritePopularMovies(): Flow<List<PopularMoviesEntity>> =
        popularMoviesDao.getFavoritePopularMovies()

    suspend fun insertPopularMovies(popularMoviesList: List<PopularMoviesEntity>) =
        popularMoviesDao.insertPopularMovies(popularMoviesList)

    fun updateFavoritePopularMovies(movies: PopularMoviesEntity, newState: Boolean) {
        movies.isFavorite = newState
        popularMoviesDao.updateFavoritePopularMovies(movies)
    }

    // Trending Movies
    fun getTrendingMovies(): Flow<List<TrendingMoviesEntity>> =
        trendingMoviesDao.getTrendingMovies()

    fun getFavoriteTrendingMovies(): Flow<List<TrendingMoviesEntity>> =
        trendingMoviesDao.getFavoriteTrendingMovies()

    suspend fun insertTrendingMovies(trendingMoviesList: List<TrendingMoviesEntity>) =
        trendingMoviesDao.insertTrendingMovies(trendingMoviesList)

    fun updateFavoriteTrendingMovies(trendingMovies: TrendingMoviesEntity, newState: Boolean) {
        trendingMovies.isFavorite = newState
        trendingMoviesDao.updateTrendingMovies(trendingMovies)
    }
}