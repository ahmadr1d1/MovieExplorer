package com.ahmadrd.movieexplorer.core.data.source.local

import com.ahmadrd.movieexplorer.core.data.source.local.entity.CastingMovieEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.DetailMovieEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.FavoriteEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.GenresMovieEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.PopularMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.SimilarMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.TrendingMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.local.room.CastingMovieDao
import com.ahmadrd.movieexplorer.core.data.source.local.room.DetailMovieDao
import com.ahmadrd.movieexplorer.core.data.source.local.room.FavoriteDao
import com.ahmadrd.movieexplorer.core.data.source.local.room.GenresMovieDao
import com.ahmadrd.movieexplorer.core.data.source.local.room.PopularMoviesDao
import com.ahmadrd.movieexplorer.core.data.source.local.room.SimilarMoviesDao
import com.ahmadrd.movieexplorer.core.data.source.local.room.TrendingMoviesDao
import com.ahmadrd.movieexplorer.core.domain.model.AllMovie
import com.ahmadrd.movieexplorer.core.utils.DataMapper.favoriteToEntity
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
    private val similarMoviesDao: SimilarMoviesDao,
    private val favoriteDao: FavoriteDao
) {

    // Popular Movies
    fun getPopularMovies(): Flow<List<PopularMoviesEntity>> =
        popularMoviesDao.getPopularMovies()

    suspend fun insertPopularMovies(popularMoviesList: List<PopularMoviesEntity>) =
        popularMoviesDao.insertPopularMovies(popularMoviesList)


    // Trending Movies
    fun getTrendingMovies(): Flow<List<TrendingMoviesEntity>> =
        trendingMoviesDao.getTrendingMovies()

    suspend fun insertTrendingMovies(trendingMoviesList: List<TrendingMoviesEntity>) =
        trendingMoviesDao.insertTrendingMovies(trendingMoviesList)


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


    // Favorites
    fun getFavorites(): Flow<List<FavoriteEntity>> = favoriteDao.getFavorites()

    fun getFavoriteById(id: Int): Flow<FavoriteEntity?> = favoriteDao.getFavoriteById(id)

    suspend fun insertFavorite(movie: FavoriteEntity) = favoriteDao.insert(movie)

    suspend fun deleteFavorite(movie: FavoriteEntity) = favoriteDao.delete(movie)

    suspend fun removeFavoriteMovie(movie: AllMovie) {
        val favoriteEntityToDelete = movie.favoriteToEntity()
        favoriteDao.delete(favoriteEntityToDelete)
    }

}