package com.ahmadrd.movieexplorer.core.domain.usecase

import com.ahmadrd.movieexplorer.core.data.Resource
import com.ahmadrd.movieexplorer.core.domain.model.AllMovie
import com.ahmadrd.movieexplorer.core.domain.model.CastingMovie
import com.ahmadrd.movieexplorer.core.domain.model.DetailMovie
import com.ahmadrd.movieexplorer.core.domain.model.GenresMovie
import com.ahmadrd.movieexplorer.core.domain.model.SimilarMovies
import com.ahmadrd.movieexplorer.core.domain.repository.IMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesInteractor @Inject constructor(private val moviesRepository: IMoviesRepository) :
    MoviesUseCase {

    // Popular Movies
    override fun getPopularMovies() = moviesRepository.getPopularMovies()

    // Trending All
    override fun getTrendingMovies() = moviesRepository.getTrendingMovies()

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

    // Favorite Movie
    override fun getFavorites(): Flow<List<AllMovie>> = moviesRepository.getFavorites()
    override suspend fun setFavorite(allMovie: AllMovie, favorite: Boolean) =
        moviesRepository.setFavorite(allMovie, favorite)
    override suspend fun removeFavorite(movie: AllMovie) =
        moviesRepository.removeFavorite(movie)
    override fun isFavorite(movieId: Int): Flow<Boolean> = moviesRepository.isFavorite(movieId)

}