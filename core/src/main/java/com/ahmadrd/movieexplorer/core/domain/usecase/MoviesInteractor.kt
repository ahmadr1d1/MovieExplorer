package com.ahmadrd.movieexplorer.core.domain.usecase

import com.ahmadrd.movieexplorer.core.domain.model.PopularMovies
import com.ahmadrd.movieexplorer.core.domain.repository.IMoviesRepository
import jakarta.inject.Inject

class MoviesInteractor @Inject constructor(private val moviesRepository: IMoviesRepository) :
    MoviesUseCase {

    override fun getPopularMovies() = moviesRepository.getPopularMovies()

    override fun getFavoritePopularMovies() = moviesRepository.getFavoritePopularMovies()

    override fun setFavoriteMovies(popularMovies: PopularMovies, state: Boolean) =
        moviesRepository.setFavoriteMovies(popularMovies, state)
}