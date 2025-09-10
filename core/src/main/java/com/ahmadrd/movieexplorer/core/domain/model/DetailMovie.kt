package com.ahmadrd.movieexplorer.core.domain.model

import android.os.Parcelable
import com.ahmadrd.movieexplorer.core.data.source.remote.response.GenresItem
import com.ahmadrd.movieexplorer.core.utils.database.GenresItemParceler
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.TypeParceler

@Parcelize
@TypeParceler<GenresItem, GenresItemParceler>
data class DetailMovie(
    val originalLanguage: String? = null,
    val imdbId: String? = null,
    val video: Boolean? = null,
    val title: String? = null,
    val backdropPath: String? = null,
    val revenue: Int? = null,
    val genres: List<GenresItem>? = null,
    val popularity: Double? = null,
    val id: Int,
    val voteCount: Int? = null,
    val budget: Int? = null,
    val overview: String? = null,
    val originalTitle: String? = null,
    val runtime: Int? = null,
    val posterPath: String? = null,
    val originCountry: List<String>? = null,
    val releaseDate: String? = null,
    val voteAverage: Double? = null,
    val tagline: String? = null,
    val adult: Boolean? = null,
    val homepage: String? = null,
    val status: String? = null
) : Parcelable