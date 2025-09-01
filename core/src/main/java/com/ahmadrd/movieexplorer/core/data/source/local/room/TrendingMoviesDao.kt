package com.ahmadrd.movieexplorer.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ahmadrd.movieexplorer.core.data.source.local.entity.TrendingMoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendingMoviesDao {

    @Query("SELECT * FROM trending_movies")
    fun getTrendingMovies(): Flow<List<TrendingMoviesEntity>>

    @Query("SELECT * FROM trending_movies where isFavorite = 1")
    fun getFavoriteTrendingMovies(): Flow<List<TrendingMoviesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingMovies(trendingMovies: List<TrendingMoviesEntity>)

    @Update
    fun updateTrendingMovies(trendingMovies: TrendingMoviesEntity)
}