package com.ahmadrd.movieexplorer.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity


/**
 * Identifiers used in the movie credits response:
 *
 * @param castId   Unique identifier for a cast entry within a single movie.
 *                 This value is only meaningful in the context of that movie's credits.
 *
 * @param id       Global identifier for the person (actor/actress) across all movies.
 *                 This ID is consistent throughout TMDB and can be used to fetch person details.
 *
 * @param movieId  Unique identifier for the movie itself.
 *                 This corresponds to the TMDB movie ID.
 */
@Entity(
    tableName = "casting_movie",
    primaryKeys = ["movieId", "id"]
)
data class CastingMovieEntity(

    val id: Int,

    val movieId: Int,

    @ColumnInfo(name = "cast_id")
    val castId: Int,

    @ColumnInfo(name = "character")
    val character: String,

    @ColumnInfo(name = "gender")
    val gender: Int,

    @ColumnInfo(name = "credit_id")
    val creditId: String,

    @ColumnInfo(name = "known_for_department")
    val knownForDepartment: String,

    @ColumnInfo(name = "original_name")
    val originalName: String,

    @ColumnInfo(name = "popularity")
    val popularity: Double,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "profile_path")
    val profilePath: String? = null,

    @ColumnInfo(name = "adult")
    val adult: Boolean,

    @ColumnInfo(name = "order")
    val order: Int
)

