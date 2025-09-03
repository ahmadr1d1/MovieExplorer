package com.ahmadrd.movieexplorer.core.data.source.remote

import android.util.Log
import com.ahmadrd.movieexplorer.core.data.source.remote.network.ApiResponse
import com.ahmadrd.movieexplorer.core.data.source.remote.network.ApiService
import com.ahmadrd.movieexplorer.core.data.source.remote.response.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    fun getPopularMovies(): Flow<ApiResponse<List<ResultsItem>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getPopularMovies()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getTrendingMovies(): Flow<ApiResponse<List<ResultsTrendingMovies>>> {
        return flow {
            try {
                val response = apiService.getTrendingMovies()
                val dataArray = response.resultsTrendingMovies
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.resultsTrendingMovies))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getGenresMovie(): Flow<ApiResponse<List<GenresItem>>> {
        return flow {
            try {
                val response = apiService.getGenres()
                val dataArray = response.genres
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.genres))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getDetailMovie(movieId: Int): Flow<ApiResponse<DetailMovieResponse>> {
        return flow {
            try {
                val response = apiService.getDetailMovie(movieId)
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getCastingMovie(movieId: Int): Flow<ApiResponse<List<CastingItem>>> {
        return flow {
            try {
                val response = apiService.getCastingMovie(movieId)
                val dataArray = response.cast
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getSimilarMovies(movieId: Int): Flow<ApiResponse<List<ResultsSimilar>>> {
        return flow {
            try {
                val response = apiService.getSimilarMovies(movieId)
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}