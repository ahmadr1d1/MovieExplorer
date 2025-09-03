package com.ahmadrd.movieexplorer.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimilarMovie(
    val id: Int,
    val overview: String,
    val originalLanguage: String,
    val originalTitle: String,
    val video: Boolean,
    val title: String,
    val genreIds: List<Int>, // Assuming similar structure to PopularMovies
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val popularity: Double,
    val voteAverage: Double,
    val adult: Boolean,
    val voteCount: Int,
    val genreNames: List<String>, // Assuming genre names will be mapped here
    var isFavorite: Boolean = false // Assuming similar favorite handling
) : Parcelable