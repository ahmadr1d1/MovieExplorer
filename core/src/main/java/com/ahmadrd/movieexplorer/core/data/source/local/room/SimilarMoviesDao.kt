package com.ahmadrd.movieexplorer.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmadrd.movieexplorer.core.data.source.local.entity.SimilarMoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SimilarMoviesDao {

    @Query("SELECT * FROM similar_movies WHERE movieId = :movieId")
    fun getSimilarMovies(movieId: Int): Flow<List<SimilarMoviesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSimilarMovies(movies: List<SimilarMoviesEntity>)

}
