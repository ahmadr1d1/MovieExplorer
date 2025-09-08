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

    val id: Int, // Cannot null error KSP Room

    val movieId: Int, // Cannot null error KSP Room

    @ColumnInfo(name = "cast_id")
    val castId: Int? = null,

    @ColumnInfo(name = "character")
    val character: String? = null,

    @ColumnInfo(name = "gender")
    val gender: Int? = null,

    @ColumnInfo(name = "credit_id")
    val creditId: String? = null,

    @ColumnInfo(name = "known_for_department")
    val knownForDepartment: String? = null,

    @ColumnInfo(name = "original_name")
    val originalName: String? = null,

    @ColumnInfo(name = "popularity")
    val popularity: Double? = null,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "profile_path")
    val profilePath: String? = null,

    @ColumnInfo(name = "adult")
    val adult: Boolean? = null,

    @ColumnInfo(name = "order")
    val order: Int? = null
)

