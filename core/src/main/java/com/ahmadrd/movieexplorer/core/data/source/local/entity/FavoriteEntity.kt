package com.ahmadrd.movieexplorer.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val id: Int,
    val overview: String? = null,
    val runtime: Int? = null,
    val originalLanguage: String? = null,
    val title: String? = null,
    val genreIds: List<Int>? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double? = null,
    val dateAdded: String
)