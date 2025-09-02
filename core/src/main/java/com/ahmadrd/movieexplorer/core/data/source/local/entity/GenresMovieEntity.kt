package com.ahmadrd.movieexplorer.core.data.source.local.entity

import androidx.room.*

@Entity(tableName = "genres_movie")
data class GenresMovieEntity (
    @PrimaryKey
    val id: Int,
    val name: String
)