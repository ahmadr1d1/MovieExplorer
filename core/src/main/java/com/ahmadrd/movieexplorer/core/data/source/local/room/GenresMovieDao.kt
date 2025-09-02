package com.ahmadrd.movieexplorer.core.data.source.local.room

import androidx.room.*
import com.ahmadrd.movieexplorer.core.data.source.local.entity.GenresMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GenresMovieDao {

    @Query("SELECT * FROM genres_movie")
    fun getGenresMovie(): Flow<List<GenresMovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenresMovie(genresMovie: List<GenresMovieEntity>)

}