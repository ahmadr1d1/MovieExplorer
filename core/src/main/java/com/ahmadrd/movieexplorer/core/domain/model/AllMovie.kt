package com.ahmadrd.movieexplorer.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AllMovie(
    val id: Int,
    val overview: String? = null,
    val runtime: Int?,
    val originalLanguage: String? = null,
    val title: String? = null,
    val genreIds: List<Int>? = null,
    val genreNames: List<String>? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Double? = null,
    val dateAdded: String,
) : Parcelable