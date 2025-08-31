package com.ahmadrd.movieexplorer.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ahmadrd.movieexplorer.core.data.source.local.entity.PopularMoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PopularMoviesDao {

    @Query("SELECT * FROM popular_movies")
    fun getPopularMovies(): Flow<List<PopularMoviesEntity>>

    @Query("SELECT * FROM popular_movies where isFavorite = 1")
    fun getFavoritePopularMovies(): Flow<List<PopularMoviesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPopularMovies(movies: List<PopularMoviesEntity>)

    @Update
    fun updateFavoritePopularMovies(movies: PopularMoviesEntity)
}