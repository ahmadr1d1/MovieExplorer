package com.ahmadrd.movieexplorer.core.domain.usecase

import com.ahmadrd.movieexplorer.core.data.Resource
import com.ahmadrd.movieexplorer.core.domain.model.PopularMovies
import kotlinx.coroutines.flow.Flow

interface MoviesUseCase {
    fun getPopularMovies(): Flow<Resource<List<PopularMovies>>>
    fun getFavoritePopularMovies(): Flow<List<PopularMovies>>
    fun setFavoriteMovies(popularMovies: PopularMovies, state: Boolean)
}