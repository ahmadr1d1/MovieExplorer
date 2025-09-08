package com.ahmadrd.movieexplorer.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ahmadrd.movieexplorer.core.data.source.remote.response.GenresItem

@Entity(tableName = "detail_movie")
data class DetailMovieEntity(

    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "original_language")
    val originalLanguage: String? = null,

    @ColumnInfo(name = "imdb_id")
    val imdbId: String? = null,

    val video: Boolean? = null,

    val title: String? = null,

    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String? = null,

    val revenue: Int? = null,

    val genres: List<GenresItem>,

    val popularity: Double? = null,

    @ColumnInfo(name = "vote_count")
    val voteCount: Int? = null,

    val budget: Int? = null,

    val overview: String? = null,

    @ColumnInfo(name = "original_title")
    val originalTitle: String? = null,

    val runtime: Int? = null,

    @ColumnInfo(name = "poster_path")
    val posterPath: String? = null,

    @ColumnInfo(name = "origin_country")
    val originCountry: List<String>? = null,

    @ColumnInfo(name = "release_date")
    val releaseDate: String? = null,

    @ColumnInfo(name = "vote_average")
    val voteAverage: Double? = null,

    val tagline: String? = null,

    val adult: Boolean? = null,

    val homepage: String? = null,

    val status: String? = null
)

