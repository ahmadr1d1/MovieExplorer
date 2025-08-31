package com.ahmadrd.movieexplorer.core.utils

import com.ahmadrd.movieexplorer.core.data.source.local.entity.PopularMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.remote.response.ResultsItem
import com.ahmadrd.movieexplorer.core.domain.model.PopularMovies

object DataMapper {
    fun mapResponsesToEntities(input: List<ResultsItem>): List<PopularMoviesEntity> {
        val popularMoviesList = ArrayList<PopularMoviesEntity>()
        input.map {
            val movies =
                PopularMoviesEntity(
                    overview = it.overview,
                    originalLanguage = it.originalLanguage,
                    originalTitle = it.originalTitle,
                    video = it.video,
                    title = it.title,
                    genreIds = it.genreIds,
                    posterPath = it.posterPath,
                    backdropPath = it.backdropPath,
                    releaseDate = it.releaseDate,
                    popularity = it.popularity,
                    voteAverage = it.voteAverage,
                    id = it.id,
                    adult = it.adult,
                    voteCount = it.voteCount,
                    isFavorite = false

                )
            popularMoviesList.add(movies)
        }
        return popularMoviesList
    }

    fun mapEntitiesToDomain(input: List<PopularMoviesEntity>): List<PopularMovies> =
        input.map {
            PopularMovies(
                overview = it.overview,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                video = it.video,
                title = it.title,
                genreIds = it.genreIds,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                id = it.id,
                adult = it.adult,
                voteCount = it.voteCount,
                isFavorite = false
            )
        }

    fun mapDomainToEntity(input: PopularMovies) =
        PopularMoviesEntity(
            overview = input.overview,
            originalLanguage = input.originalLanguage,
            originalTitle = input.originalTitle,
            video = input.video,
            title = input.title,
            genreIds = input.genreIds,
            posterPath = input.posterPath,
            backdropPath = input.backdropPath,
            releaseDate = input.releaseDate,
            popularity = input.popularity,
            voteAverage = input.voteAverage,
            id = input.id,
            adult = input.adult,
            voteCount = input.voteCount,
            isFavorite = false

        )
}