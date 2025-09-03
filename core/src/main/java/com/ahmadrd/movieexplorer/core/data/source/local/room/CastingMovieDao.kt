package com.ahmadrd.movieexplorer.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmadrd.movieexplorer.core.data.source.local.entity.CastingMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CastingMovieDao {

    @Query("SELECT * FROM casting_movie WHERE movieId = :movieId")
    fun getCastingMovie(movieId: Int): Flow<List<CastingMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCastingMovie(cast: List<CastingMovieEntity>)

}
