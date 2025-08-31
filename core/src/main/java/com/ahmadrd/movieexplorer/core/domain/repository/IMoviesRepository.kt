package com.ahmadrd.movieexplorer.core.domain.repository

import com.ahmadrd.movieexplorer.core.data.Resource
import com.ahmadrd.movieexplorer.core.domain.model.PopularMovies
import kotlinx.coroutines.flow.Flow

interface IMoviesRepository {

    fun getPopularMovies(): Flow<Resource<List<PopularMovies>>>

    fun getFavoritePopularMovies(): Flow<List<PopularMovies>>

    fun setFavoriteMovies(popularMovies: PopularMovies, state: Boolean)
}