package com.ahmadrd.movieexplorer.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmadrd.movieexplorer.core.data.source.local.entity.TrendingMoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendingMoviesDao {

    @Query("SELECT * FROM trending_movies")
    fun getTrendingMovies(): Flow<List<TrendingMoviesEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingMovies(trendingMovies: List<TrendingMoviesEntity>)
}