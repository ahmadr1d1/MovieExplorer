package com.ahmadrd.movieexplorer.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailMovie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val tagline: String?,
    val status: String,
    val runtime: Int?,
    val originalLanguage: String,
    val originalTitle: String,
    val popularity: Double,
    val homepage: String?,
    val imdbId: String?,
    val adult: Boolean,
    val genres: List<String>, // Assuming genre names will be mapped here
    var isFavorite: Boolean = false // Assuming similar favorite handling
) : Parcelable