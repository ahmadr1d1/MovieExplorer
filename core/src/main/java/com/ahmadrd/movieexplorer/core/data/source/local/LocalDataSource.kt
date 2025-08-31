package com.ahmadrd.movieexplorer.core.data.source.local

import com.ahmadrd.movieexplorer.core.data.source.local.entity.PopularMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.local.room.PopularMoviesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val popularMoviesDao: PopularMoviesDao) {

    fun getPopularMovies(): Flow<List<PopularMoviesEntity>> = popularMoviesDao.getPopularMovies()

    fun getFavoritePopularMovies(): Flow<List<PopularMoviesEntity>> = popularMoviesDao.getFavoritePopularMovies()

    suspend fun insertPopularMovies(tourismList: List<PopularMoviesEntity>) = popularMoviesDao.insertPopularMovies(tourismList)

    fun updateFavoritePopularMovies(movies: PopularMoviesEntity, newState: Boolean) {
        movies.isFavorite = newState
        popularMoviesDao.updateFavoritePopularMovies(movies)
    }
}