package com.ahmadrd.movieexplorer.core.domain.usecase

import com.ahmadrd.movieexplorer.core.domain.model.PopularMovies
import com.ahmadrd.movieexplorer.core.domain.model.TrendingMovies
import com.ahmadrd.movieexplorer.core.domain.repository.IMoviesRepository
import javax.inject.Inject

class MoviesInteractor @Inject constructor(private val moviesRepository: IMoviesRepository) :
    MoviesUseCase {

    // Popular Movies
    override fun getPopularMovies() = moviesRepository.getPopularMovies()
    override fun getFavoritePopularMovies() = moviesRepository.getFavoritePopularMovies()
    override fun setFavoriteMovies(popularMovies: PopularMovies, state: Boolean) =
        moviesRepository.setFavoriteMovies(popularMovies, state)

    // Trending All
    override fun getTrendingMovies() = moviesRepository.getTrendingMovies()
    override fun getFavoriteTrendingMovies() = moviesRepository.getFavoriteTrendingMovies()
    override fun setFavoriteTrendingMovies(trendingMovies: TrendingMovies, state: Boolean) =
        moviesRepository.setFavoriteTrendingMovies(trendingMovies, state)

}