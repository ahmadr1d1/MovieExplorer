package com.ahmadrd.movieexplorer.core.data

import com.ahmadrd.movieexplorer.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.*

abstract class NetworkBoundResource<ResultType, RequestType> {
    protected abstract fun loadFromDB(): Flow<ResultType?>
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>
    protected abstract suspend fun saveCallResult(data: RequestType)
    protected open fun onFetchFailed() {}

    fun asFlow(): Flow<Resource<ResultType>> = flow {
        // First, check current data from DB
        val dbSource = loadFromDB().firstOrNull()

        // Second, fetch data from API and save to DB
        if (shouldFetch(dbSource)) {
            try {
                emit(Resource.Loading())

                createCall().collect { apiResponse ->
                    when (apiResponse) {
                        is ApiResponse.Success -> {
                            try {
                                // Save to database FIRST
                                saveCallResult(apiResponse.data)

                                // Then emit fresh data from database
                                // This ensures data is actually saved before emitting
                                loadFromDB().collect { freshData ->
                                    if (freshData != null) {
                                        emit(Resource.Success(freshData))
                                    } else {
                                        emit(Resource.Error("Failed to save data to database"))
                                    }
                                }
                            } catch (dbException: Exception) {
                                // Specific database error handling
                                emit(Resource.Error("Failed to save data: ${dbException.message}"))
                            }
                        }
                        is ApiResponse.Empty -> {
                            // For empty response, emit current DB data or error
                            emit(dbSource?.let { Resource.Success(it) }
                                ?: Resource.Error("No cached data available"))
                        }
                        is ApiResponse.Error -> {
                            onFetchFailed()
                            emit(Resource.Error(apiResponse.errorMessage))
                        }
                    }
                }
            } catch (e: Exception) {
                // Unexpected flow errors
                onFetchFailed()
                emit(Resource.Error(e.message ?: "Unknown error occurred"))
            }
        } else { // Third, if don't need to fetch(dbSource not null), use DB data
            emit(Resource.Loading())
            dbSource?.let {
                emit(Resource.Success(it))
                // Continue observing DB changes for updates
                loadFromDB().collect { data ->
                    if (data != null) {
                        emit(Resource.Success(data))
                    }
                }
            } ?: emit(Resource.Error("No data available"))
        }
    }
}
