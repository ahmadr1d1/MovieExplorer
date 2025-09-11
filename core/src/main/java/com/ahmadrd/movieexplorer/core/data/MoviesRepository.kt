package com.ahmadrd.movieexplorer.core.data

import com.ahmadrd.movieexplorer.core.data.source.local.LocalDataSource
import com.ahmadrd.movieexplorer.core.data.source.local.entity.GenresMovieEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.SimilarMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.remote.RemoteDataSource
import com.ahmadrd.movieexplorer.core.data.source.remote.network.ApiResponse
import com.ahmadrd.movieexplorer.core.data.source.remote.response.*
import com.ahmadrd.movieexplorer.core.domain.model.*
import com.ahmadrd.movieexplorer.core.domain.repository.IMoviesRepository
import com.ahmadrd.movieexplorer.core.utils.DataMapper
import com.ahmadrd.movieexplorer.core.utils.DataMapper.favoriteToDomain
import com.ahmadrd.movieexplorer.core.utils.DataMapper.favoriteToEntity
import com.ahmadrd.movieexplorer.core.utils.DataMapper.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IMoviesRepository {

    // Popular Movies
    override fun getPopularMovies(): Flow<Resource<List<PopularMovies>>> =
        object : NetworkBoundResource<List<PopularMovies>, List<ResultsItem>>() {
            override fun loadFromDB(): Flow<List<PopularMovies>> {
                val popularMovieEntitiesFlow = localDataSource.getPopularMovies()
                val allGenreEntitiesFlow = localDataSource.getGenresMovie()

                // Get genres name
                return popularMovieEntitiesFlow.combine(allGenreEntitiesFlow) { movieEntities, genreEntities ->
                    val allGenresDomain = DataMapper.mapGenreEntitiesToDomain(genreEntities)
                    DataMapper.mapPopularMoviesEntitiesToDomain(movieEntities, allGenresDomain)
                }
            }

            override fun shouldFetch(data: List<PopularMovies>?): Boolean =
                data.isNullOrEmpty() // Set true if want to always fetch from remote API

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getPopularMovies()

            override suspend fun saveCallResult(data: List<ResultsItem>) {
                val popularMoviesList = DataMapper.mapPopularMoviesResponsesToEntities(data)
                localDataSource.insertPopularMovies(popularMoviesList)
            }
        }.asFlow()


    // Trending Movies
    override fun getTrendingMovies(): Flow<Resource<List<TrendingMovies>>> =
        object : NetworkBoundResource<List<TrendingMovies>, List<ResultsTrendingMovies>>() {
            override fun loadFromDB(): Flow<List<TrendingMovies>> {
                val trendingMoviesEntitiesFlow = localDataSource.getTrendingMovies()
                val allGenreEntitiesFlow = localDataSource.getGenresMovie()

                // Get genres name
                return trendingMoviesEntitiesFlow.combine(allGenreEntitiesFlow) { movieEntities, genreEntities ->
                    val allGenresDomain = DataMapper.mapGenreEntitiesToDomain(genreEntities)
                    DataMapper.mapTrendingMoviesEntitiesToDomain(movieEntities, allGenresDomain)
                }
            }

            override fun shouldFetch(data: List<TrendingMovies>?): Boolean =
                data.isNullOrEmpty() // Set true if want to always fetch from remote API

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsTrendingMovies>>> =
                remoteDataSource.getTrendingMovies()

            override suspend fun saveCallResult(data: List<ResultsTrendingMovies>) {
                val trendingMoviesList = DataMapper.mapTrendingMoviesResponsesToEntities(data)
                localDataSource.insertTrendingMovies(trendingMoviesList)
            }
        }.asFlow()


    // Genres Movie
    override fun getGenresMovie(): Flow<Resource<List<GenresMovie>>> =
        object : NetworkBoundResource<List<GenresMovie>, List<GenresItem>>() {
            override fun loadFromDB(): Flow<List<GenresMovie>> {
                return localDataSource.getGenresMovie().map {
                    DataMapper.mapGenreEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<GenresMovie>?): Boolean =
                data.isNullOrEmpty() // Set true if want to always fetch from remote API

            override suspend fun createCall(): Flow<ApiResponse<List<GenresItem>>> =
                remoteDataSource.getGenresMovie()

            override suspend fun saveCallResult(data: List<GenresItem>) {
                val genresList = DataMapper.mapGenreResponsesToEntities(data)
                localDataSource.insertGenresMovie(genresList)
            }
        }.asFlow()


    // Detail Movie
    override fun getDetailMovie(movieId: Int): Flow<Resource<DetailMovie>> =
        object : NetworkBoundResource<DetailMovie, DetailMovieResponse>() {
            override fun loadFromDB(): Flow<DetailMovie?> {
                return localDataSource.getDetailMovie(movieId)
                    .map { it?.toDomain() }
            }

            override fun shouldFetch(data: DetailMovie?): Boolean =
                data == null // Set true if want to always fetch from remote API

            override suspend fun createCall(): Flow<ApiResponse<DetailMovieResponse>> =
                remoteDataSource.getDetailMovie(movieId)

            override suspend fun saveCallResult(data: DetailMovieResponse) {
                val entity = DataMapper.mapDetailMovieResponseToEntities(data)
                localDataSource.insertDetailMovie(entity)
            }
        }.asFlow()


    // Casting Movie
    override fun getCastingMovie(movieId: Int): Flow<Resource<List<CastingMovie>>> =
        object : NetworkBoundResource<List<CastingMovie>, List<CastingItem>>() {

            override fun loadFromDB(): Flow<List<CastingMovie>> {
                return localDataSource.getCastingMovie(movieId).map { entities ->
                    entities.toDomain()
                }
            }

            override fun shouldFetch(data: List<CastingMovie>?): Boolean =
                data.isNullOrEmpty() // Set true if want to always fetch from remote API

            override suspend fun createCall(): Flow<ApiResponse<List<CastingItem>>> =
                remoteDataSource.getCastingMovie(movieId)

            override suspend fun saveCallResult(data: List<CastingItem>) {
                val castingMovieList = DataMapper.mapCastingMovieResponsesToEntities(movieId, data)
                localDataSource.insertCastingMovie(castingMovieList)
            }
        }.asFlow()


    // Similar Movies
    override fun getSimilarMovies(movieId: Int): Flow<Resource<List<SimilarMovies>>> =
        object : NetworkBoundResource<List<SimilarMovies>, List<ResultsSimilar>>() {

            override fun loadFromDB(): Flow<List<SimilarMovies>> {
                val similarMovieEntitiesFlow: Flow<List<SimilarMoviesEntity>> = localDataSource.getSimilarMovies(movieId)
                val allGenreEntitiesFlow: Flow<List<GenresMovieEntity>> = localDataSource.getGenresMovie()

                // Get genres name
                return similarMovieEntitiesFlow.combine(allGenreEntitiesFlow) { movieEntities, genreEntities ->
                    val allGenresDomain: List<GenresMovie> = DataMapper.mapGenreEntitiesToDomain(genreEntities)
                    movieEntities.toDomain(allGenresDomain)
                }
            }

            override fun shouldFetch(data: List<SimilarMovies>?): Boolean =
                data.isNullOrEmpty() // Set true if want to always fetch from remote API

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsSimilar>>> =
                remoteDataSource.getSimilarMovies(movieId)

            override suspend fun saveCallResult(data: List<ResultsSimilar>) {
                val similarMoviesList = DataMapper.mapSimilarMovieResponsesToEntities(movieId, data)
                localDataSource.insertSimilarMovies(similarMoviesList)
            }
        }.asFlow()



    // Favorites
    override fun getFavorites(): Flow<List<AllMovie>> {
        val favoritesFlow = localDataSource.getFavorites()
        val genresFlow = localDataSource.getGenresMovie()

        // Get genres name
        return favoritesFlow.combine(genresFlow) { movieEntities, genreEntities ->
            val allGenresDomain: List<GenresMovie> = DataMapper.mapGenreEntitiesToDomain(genreEntities)
            movieEntities.favoriteToDomain(allGenresDomain)
        }
    }

    override suspend fun setFavorite(
        allMovie: AllMovie,
        favorite: Boolean
    ) {
        if (favorite) {
            localDataSource.insertFavorite(allMovie.favoriteToEntity())
        } else {
            localDataSource.deleteFavorite(allMovie.favoriteToEntity())
        }
    }

    override suspend fun removeFavorite(movie: AllMovie) {
        localDataSource.removeFavoriteMovie(movie)
    }

    override fun isFavorite(movieId: Int): Flow<Boolean> =
        localDataSource.getFavoriteById(movieId).map { it != null }
}