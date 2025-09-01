package com.ahmadrd.movieexplorer.core.utils

import com.ahmadrd.movieexplorer.core.data.source.local.entity.PopularMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.TrendingMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.remote.response.ResultsItem
import com.ahmadrd.movieexplorer.core.data.source.remote.response.ResultsTrendingMovies
import com.ahmadrd.movieexplorer.core.domain.model.PopularMovies
import com.ahmadrd.movieexplorer.core.domain.model.TrendingMovies

object DataMapper {

    // Popular Movies
    fun mapPMoviesResponsesToEntities(input: List<ResultsItem>): List<PopularMoviesEntity> {
        val popularMoviesList = ArrayList<PopularMoviesEntity>()
        input.map {
            val movie =
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
            popularMoviesList.add(movie)
        }
        return popularMoviesList
    }

    fun mapPMoviesEntitiesToDomain(input: List<PopularMoviesEntity>): List<PopularMovies> =
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

    fun mapPMoviesDomainToEntity(input: PopularMovies) =
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


    // Trending Movies
    fun mapTrendingMoviesResponsesToEntities(
        input: List<ResultsTrendingMovies>
    ): List<TrendingMoviesEntity> {
        val trendingMoviesList = ArrayList<TrendingMoviesEntity>()
        input.map {
            val movie = TrendingMoviesEntity(
                overview = it.overview,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                video = it.video,
                title = it.title,
                genreIds = it.genreIds,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                mediaType = it.mediaType,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                id = it.id,
                adult = it.adult,
                voteCount = it.voteCount
            )
            trendingMoviesList.add(movie)
        }
        return trendingMoviesList
    }


    fun mapTMoviesEntitiesToDomain(input: List<TrendingMoviesEntity>): List<TrendingMovies> =
        input.map {
            TrendingMovies(
                overview = it.overview,
                originalLanguage = it.originalLanguage,
                originalTitle = it.originalTitle,
                video = it.video,
                title = it.title,
                genreIds = it.genreIds,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                mediaType = it.mediaType,
                releaseDate = it.releaseDate,
                popularity = it.popularity,
                voteAverage = it.voteAverage,
                id = it.id,
                adult = it.adult,
                voteCount = it.voteCount
            )
        }

    fun mapTMoviesDomainToEntity(input: TrendingMovies) =
        TrendingMoviesEntity(
            overview = input.overview,
            originalLanguage = input.originalLanguage,
            originalTitle = input.originalTitle,
            video = input.video,
            title = input.title,
            genreIds = input.genreIds,
            posterPath = input.posterPath,
            backdropPath = input.backdropPath,
            mediaType = input.mediaType,
            releaseDate = input.releaseDate,
            popularity = input.popularity,
            voteAverage = input.voteAverage,
            id = input.id,
            adult = input.adult,
            voteCount = input.voteCount
        )
}