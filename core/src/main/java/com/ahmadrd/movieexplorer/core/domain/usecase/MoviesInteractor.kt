package com.ahmadrd.movieexplorer.core.domain.usecase

import com.ahmadrd.movieexplorer.core.data.Resource
import com.ahmadrd.movieexplorer.core.domain.model.CastingMovie
import com.ahmadrd.movieexplorer.core.domain.model.DetailMovie
import com.ahmadrd.movieexplorer.core.domain.model.GenresMovie
import com.ahmadrd.movieexplorer.core.domain.model.PopularMovies
import com.ahmadrd.movieexplorer.core.domain.model.SimilarMovies
import com.ahmadrd.movieexplorer.core.domain.model.TrendingMovies
import com.ahmadrd.movieexplorer.core.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow
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

    // Genres Movie
    override fun getGenresMovie(): Flow<Resource<List<GenresMovie>>> =
        moviesRepository.getGenresMovie()

    // Detail Movie
    override fun getDetailMovie(movieId: Int): Flow<Resource<DetailMovie>> =
        moviesRepository.getDetailMovie(movieId)

    // Casting Movie
    override fun getCastingMovie(movieId: Int): Flow<Resource<List<CastingMovie>>> =
        moviesRepository.getCastingMovie(movieId)

    // Similar Movie
    override fun getSimilarMovies(movieId: Int): Flow<Resource<List<SimilarMovies>>> =
        moviesRepository.getSimilarMovies(movieId)

}