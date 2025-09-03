package com.ahmadrd.movieexplorer.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detail_movie")
data class DetailMovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String?,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int,
    val status: String,
    val tagline: String?,
    val homepage: String?,
    @ColumnInfo(name = "runtime")
    val runtime: Int?,
    // Add other relevant fields for detail movie
)
