package com.ahmadrd.movieexplorer.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmadrd.movieexplorer.core.data.source.local.entity.DetailMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailMovieDao {

    @Query("SELECT * FROM detail_movie WHERE id = :movieId")
    fun getDetailMovie(movieId: Int): Flow<DetailMovieEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDetailMovie(movie: DetailMovieEntity)

}
