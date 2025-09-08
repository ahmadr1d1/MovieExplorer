package com.ahmadrd.movieexplorer.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Identifiers used in the movie similar response.
 *
 * @param id      The identifier of the similar movie.
 * @param movieId The identifier of the detailed movie.
 */
@Parcelize
data class SimilarMovies(
    val id: Int,
    val movieId: Int,
    val overview: String? = null,
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val video: Boolean? = null,
    val title: String? = null,
    val genreIds: List<Int>? = null,
    val posterPath: String? = null,
    val backdropPath: String? = null,
    val releaseDate: String? = null,
    val popularity: Double? = null,
    val voteAverage: Double? = null,
    val adult: Boolean? = null,
    val genreNames: List<String>? = null,
    val voteCount: Int? = null
) : Parcelable