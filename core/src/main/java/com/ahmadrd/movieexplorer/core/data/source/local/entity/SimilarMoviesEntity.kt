package com.ahmadrd.movieexplorer.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity


/**
 * Identifiers used in the movie similar response.
 *
 * @param id      The identifier of the similar movie.
 * @param movieId The identifier of the detailed movie.
 */

@Entity(
    tableName = "similar_movies",
    primaryKeys = ["movieId", "id"]
)
data class SimilarMoviesEntity(

    val id: Int, // Cannot null error KSP Room

    val movieId: Int, // Cannot null error KSP Room

    @ColumnInfo(name = "overview")
    val overview: String? = null,

    @ColumnInfo(name = "original_language")
    val originalLanguage: String? = null,

    @ColumnInfo(name = "original_title")
    val originalTitle: String? = null,

    @ColumnInfo(name = "video")
    val video: Boolean? = null,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "genre_ids")
    val genreIds: List<Int>? = null,

    @ColumnInfo(name = "poster_path")
    val posterPath: String? = null,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String? = null,

    @ColumnInfo(name = "release_date")
    val releaseDate: String? = null,

    @ColumnInfo(name = "popularity")
    val popularity: Double? = null,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double? = null,

    @ColumnInfo(name = "adult")
    val adult: Boolean? = null,

    @ColumnInfo(name = "vote_count")
    val voteCount: Int? = null
)


