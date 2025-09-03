package com.ahmadrd.movieexplorer.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "similar_movies")
data class SimilarMoviesEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "movie_id") // To associate with a movie for which similar movies are fetched
    val movieId: Int,
    val title: String,
    val overview: String,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
    @ColumnInfo(name = "release_date")
    val releaseDate: String,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double,
    // Add other relevant fields for similar movies
)
