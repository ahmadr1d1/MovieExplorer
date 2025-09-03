package com.ahmadrd.movieexplorer.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

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
@Parcelize
data class CastingMovie(
    val movieId: Int,
    val id: Int,
    val castId: Int,
    val character: String,
    val gender: Int,
    val creditId: String,
    val knownForDepartment: String,
    val originalName: String,
    val popularity: Double,
    val name: String,
    val profilePath: String? = null,
    val adult: Boolean,
    val order: Int
) : Parcelable
