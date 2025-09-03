package com.ahmadrd.movieexplorer.core.data

import com.ahmadrd.movieexplorer.core.data.source.local.LocalDataSource
import com.ahmadrd.movieexplorer.core.data.source.remote.RemoteDataSource
import com.ahmadrd.movieexplorer.core.data.source.remote.network.ApiResponse
import com.ahmadrd.movieexplorer.core.data.source.remote.response.*
import com.ahmadrd.movieexplorer.core.domain.model.*
import com.ahmadrd.movieexplorer.core.domain.repository.IMoviesRepository
import com.ahmadrd.movieexplorer.core.utils.AppExecutors
import com.ahmadrd.movieexplorer.core.utils.DataMapper
import com.ahmadrd.movieexplorer.core.utils.DataMapper.toDomain
import com.ahmadrd.movieexplorer.core.utils.DataMapper.toEntities
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IMoviesRepository {

    override fun getPopularMovies(): Flow<Resource<List<PopularMovies>>> =
        object : NetworkBoundResource<List<PopularMovies>, List<ResultsItem>>() {
            override fun loadFromDB(): Flow<List<PopularMovies>> {
                val popularMovieEntitiesFlow = localDataSource.getPopularMovies()
                val allGenreEntitiesFlow = localDataSource.getGenresMovie()

                // Mendapatkan nama genre
                return popularMovieEntitiesFlow.combine(allGenreEntitiesFlow) { movieEntities, genreEntities ->
                    val allGenresDomain = DataMapper.mapGenreEntitiesToDomain(genreEntities)
                    DataMapper.mapPMoviesEntitiesToDomain(movieEntities, allGenresDomain)
                }
            }

            override fun shouldFetch(data: List<PopularMovies>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getPopularMovies()

            override suspend fun saveCallResult(data: List<ResultsItem>) {
                val popularMoviesList = DataMapper.mapPMoviesResponsesToEntities(data)
                localDataSource.insertPopularMovies(popularMoviesList)
            }
        }.asFlow()

    override fun getFavoritePopularMovies(): Flow<List<PopularMovies>> {
        val favoriteMovieEntitiesFlow = localDataSource.getFavoritePopularMovies()
        val allGenreEntitiesFlow = localDataSource.getGenresMovie()

        return favoriteMovieEntitiesFlow.combine(allGenreEntitiesFlow) { movieEntities, genreEntities ->
            val allGenresDomain = DataMapper.mapGenreEntitiesToDomain(genreEntities)
            DataMapper.mapPMoviesEntitiesToDomain(movieEntities, allGenresDomain)
        }
    }

    override fun setFavoriteMovies(popularMovies: PopularMovies, state: Boolean) {
        val moviesEntity = DataMapper.mapPMoviesDomainToEntity(popularMovies)
        appExecutors.diskIO()
            .execute {
                localDataSource.updateFavoritePopularMovies(
                    moviesEntity, state
                )
            }
    }

    override fun getTrendingMovies(): Flow<Resource<List<TrendingMovies>>> =
        object : NetworkBoundResource<List<TrendingMovies>, List<ResultsTrendingMovies>>() {
            override fun loadFromDB(): Flow<List<TrendingMovies>> {
                val trendingMoviesEntitiesFlow = localDataSource.getTrendingMovies()
                val allGenreEntitiesFlow = localDataSource.getGenresMovie()

                // Mendapatkan nama genre
                return trendingMoviesEntitiesFlow.combine(allGenreEntitiesFlow) { movieEntities, genreEntities ->
                    val allGenresDomain = DataMapper.mapGenreEntitiesToDomain(genreEntities)
                    DataMapper.mapTMoviesEntitiesToDomain(movieEntities, allGenresDomain)
                }
            }

            override fun shouldFetch(data: List<TrendingMovies>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsTrendingMovies>>> =
                remoteDataSource.getTrendingMovies()

            override suspend fun saveCallResult(data: List<ResultsTrendingMovies>) {
                val trendingMoviesList = DataMapper.mapTrendingMoviesResponsesToEntities(data)
                localDataSource.insertTrendingMovies(trendingMoviesList)
            }
        }.asFlow()

    override fun getFavoriteTrendingMovies(): Flow<List<TrendingMovies>> {
        val trendingMoviesEntitiesFlow = localDataSource.getTrendingMovies()
        val allGenreEntitiesFlow = localDataSource.getGenresMovie()

        // Mendapatkan nama genre
        return trendingMoviesEntitiesFlow.combine(allGenreEntitiesFlow) { movieEntities, genreEntities ->
            val allGenresDomain = DataMapper.mapGenreEntitiesToDomain(genreEntities)
            DataMapper.mapTMoviesEntitiesToDomain(movieEntities, allGenresDomain)
        }
    }

    override fun setFavoriteTrendingMovies(trendingMovies: TrendingMovies, state: Boolean) =
        appExecutors.diskIO()
            .execute {
                localDataSource.updateFavoriteTrendingMovies(
                    DataMapper.
                    mapTMoviesDomainToEntity(trendingMovies), state
                )
            }

    override fun getGenresMovie(): Flow<Resource<List<GenresMovie>>> =
        object : NetworkBoundResource<List<GenresMovie>, List<GenresItem>>() {
            override fun loadFromDB(): Flow<List<GenresMovie>> {
                return localDataSource.getGenresMovie().map {
                    DataMapper.mapGenreEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<GenresMovie>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<GenresItem>>> =
                remoteDataSource.getGenresMovie()

            override suspend fun saveCallResult(data: List<GenresItem>) {
                val genresList = DataMapper.mapGenreResponsesToEntities(data)
                localDataSource.insertGenresMovie(genresList)
            }
        }.asFlow()

    override fun getDetailMovie(movieId: Int): Flow<Resource<DetailMovie>> {
        TODO("Not yet implemented")
    }

    override fun getCastingMovie(movieId: Int): Flow<Resource<List<CastingMovie>>> =
        object : NetworkBoundResource<List<CastingMovie>, List<CastingItem>>() {

            override fun loadFromDB(): Flow<List<CastingMovie>> {
                return localDataSource.getCastingMovie(movieId).map { entities ->
                    entities.toDomain()
                }
            }

            override fun shouldFetch(data: List<CastingMovie>?): Boolean =
                data.isNullOrEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<CastingItem>>> =
                remoteDataSource.getCastingMovie(movieId)

            override suspend fun saveCallResult(data: List<CastingItem>) {
                val entities = data.toEntities(movieId)
                localDataSource.insertCastingMovie(entities)
            }
        }.asFlow()


    override fun getSimilarMovies(movieId: Int): Flow<Resource<List<SimilarMovie>>> {
        TODO("Not yet implemented")
    }
}