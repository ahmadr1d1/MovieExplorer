package com.ahmadrd.movieexplorer.core.data

import com.ahmadrd.movieexplorer.core.data.source.local.LocalDataSource
import com.ahmadrd.movieexplorer.core.data.source.remote.RemoteDataSource
import com.ahmadrd.movieexplorer.core.data.source.remote.network.ApiResponse
import com.ahmadrd.movieexplorer.core.data.source.remote.response.ResultsItem
import com.ahmadrd.movieexplorer.core.domain.model.PopularMovies
import com.ahmadrd.movieexplorer.core.domain.repository.IMoviesRepository
import com.ahmadrd.movieexplorer.core.utils.AppExecutors
import com.ahmadrd.movieexplorer.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
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
                return localDataSource.getPopularMovies().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<PopularMovies>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsItem>>> =
                remoteDataSource.getPopularMovies()

            override suspend fun saveCallResult(data: List<ResultsItem>) {
                val popularMoviesList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertPopularMovies(popularMoviesList)
            }
        }.asFlow()

    override fun getFavoritePopularMovies(): Flow<List<PopularMovies>> {
        return localDataSource.getFavoritePopularMovies().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteMovies(popularMovies: PopularMovies, state: Boolean) {
        val moviesEntity = DataMapper.mapDomainToEntity(popularMovies)
        appExecutors.diskIO()
            .execute { localDataSource.updateFavoritePopularMovies(
                moviesEntity, state)
            }
    }
}