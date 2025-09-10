package com.ahmadrd.movieexplorer.core.data.source.local.room

import androidx.room.*
import com.ahmadrd.movieexplorer.core.data.source.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    fun getFavorites(): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites WHERE id = :id LIMIT 1")
    fun getFavoriteById(id: Int): Flow<FavoriteEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FavoriteEntity)

    @Delete
    suspend fun delete(entity: FavoriteEntity)
}