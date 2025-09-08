package com.ahmadrd.movieexplorer.core.data.source.local

import com.ahmadrd.movieexplorer.core.data.source.local.entity.CastingMovieEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.DetailMovieEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.GenresMovieEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.PopularMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.SimilarMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.TrendingMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.local.room.CastingMovieDao
import com.ahmadrd.movieexplorer.core.data.source.local.room.DetailMovieDao
import com.ahmadrd.movieexplorer.core.data.source.local.room.GenresMovieDao
import com.ahmadrd.movieexplorer.core.data.source.local.room.PopularMoviesDao
import com.ahmadrd.movieexplorer.core.data.source.local.room.SimilarMoviesDao
import com.ahmadrd.movieexplorer.core.data.source.local.room.TrendingMoviesDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val popularMoviesDao: PopularMoviesDao,
    private val trendingMoviesDao: TrendingMoviesDao,
    private val genresMovieDao: GenresMovieDao,
    private val detailMovieDao: DetailMovieDao,
    private val castingMovieDao: CastingMovieDao,
    private val similarMoviesDao: SimilarMoviesDao
) {

    // Popular Movies
    fun getPopularMovies(): Flow<List<PopularMoviesEntity>> =
        popularMoviesDao.getPopularMovies()

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

    // Genres Movie
    fun getGenresMovie(): Flow<List<GenresMovieEntity>> = genresMovieDao.getGenresMovie()

    suspend fun insertGenresMovie(genres: List<GenresMovieEntity>) =
        genresMovieDao.insertGenresMovie(genres)

    // Detail Movie
    fun getDetailMovie(movieId: Int): Flow<DetailMovieEntity?> =
        detailMovieDao.getDetailMovie(movieId)

    suspend fun insertDetailMovie(movie: DetailMovieEntity) =
        detailMovieDao.insertDetailMovie(movie)

    // Casting Movie
    fun getCastingMovie(movieId: Int): Flow<List<CastingMovieEntity>> =
        castingMovieDao.getCastingMovie(movieId)

    suspend fun insertCastingMovie(cast: List<CastingMovieEntity>) =
        castingMovieDao.insertCastingMovie(cast)

    // Similar Movies
    fun getSimilarMovies(movieId: Int): Flow<List<SimilarMoviesEntity>> =
        similarMoviesDao.getSimilarMovies(movieId)

    suspend fun insertSimilarMovies(movies: List<SimilarMoviesEntity>) =
        similarMoviesDao.insertSimilarMovies(movies)

}