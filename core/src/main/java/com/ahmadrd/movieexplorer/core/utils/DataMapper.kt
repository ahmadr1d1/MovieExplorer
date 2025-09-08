package com.ahmadrd.movieexplorer.core.utils

import com.ahmadrd.movieexplorer.core.data.source.local.entity.CastingMovieEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.DetailMovieEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.GenresMovieEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.PopularMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.SimilarMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.TrendingMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.remote.response.*
import com.ahmadrd.movieexplorer.core.domain.model.*

object DataMapper {

    // Popular Movies
    fun mapPopularMoviesResponsesToEntities(input: List<ResultsItem>?): List<PopularMoviesEntity> {
        return input?.map {
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
                id = it.id ?: -1,
                adult = it.adult,
                voteCount = it.voteCount,
                isFavorite = false
            )
        } ?: emptyList()
    }

    fun mapPopularMoviesEntitiesToDomain(
        input: List<PopularMoviesEntity>,
        allGenresDomain: List<GenresMovie>
    ): List<PopularMovies> {
        val genreMap = allGenresDomain.associateBy({ it.id }, { it.name })
        return input.map { entity ->
            val genreNames = entity.genreIds?.mapNotNull { id -> genreMap[id] }
            PopularMovies(
                id = entity.id,
                title = entity.title,
                overview = entity.overview,
                originalLanguage = entity.originalLanguage,
                originalTitle = entity.originalTitle,
                video = entity.video,
                genreIds = entity.genreIds,
                genreNames = genreNames, // From mapping
                posterPath = entity.posterPath,
                backdropPath = entity.backdropPath,
                releaseDate = entity.releaseDate,
                popularity = entity.popularity,
                voteAverage = entity.voteAverage,
                adult = entity.adult,
                voteCount = entity.voteCount,
                isFavorite = entity.isFavorite
            )
        }
    }

    fun mapPopularMoviesDomainToEntity(input: PopularMovies) =
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
            isFavorite = input.isFavorite
        )


    // Trending Movies
    fun mapTrendingMoviesResponsesToEntities(
        input: List<ResultsTrendingMovies>?
    ): List<TrendingMoviesEntity> {
        return input?.map {
            TrendingMoviesEntity(
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
                id = it.id ?: -1,
                adult = it.adult,
                voteCount = it.voteCount
            )
        } ?: emptyList()
    }


    fun mapTrendingMoviesEntitiesToDomain(
        input: List<TrendingMoviesEntity>,
        allGenresDomain: List<GenresMovie>
    ): List<TrendingMovies> {
        val genreMap = allGenresDomain.associateBy({ it.id }, { it.name })
        return input.map { entity ->
            val genreNames = entity.genreIds?.mapNotNull { id -> genreMap[id] }
            TrendingMovies(
                id = entity.id,
                title = entity.title,
                overview = entity.overview,
                originalLanguage = entity.originalLanguage,
                originalTitle = entity.originalTitle,
                video = entity.video,
                genreIds = entity.genreIds,
                genreNames = genreNames, // From mapping
                posterPath = entity.posterPath,
                backdropPath = entity.backdropPath,
                mediaType = entity.mediaType,
                releaseDate = entity.releaseDate,
                popularity = entity.popularity,
                voteAverage = entity.voteAverage,
                adult = entity.adult,
                voteCount = entity.voteCount,
                isFavorite = entity.isFavorite
            )
        }
    }

    fun mapTrendingMoviesDomainToEntity(input: TrendingMovies) =
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


    // Genres Movie
    fun mapGenreResponsesToEntities(input: List<GenresItem>?): List<GenresMovieEntity> {
        return input?.map {
            GenresMovieEntity(
                id = it.id ?: -1,
                name = it.name
            )
        } ?: emptyList()
    }

    fun mapGenreEntitiesToDomain(input: List<GenresMovieEntity>): List<GenresMovie> {
        return input.map {
            GenresMovie(
                id = it.id,
                name = it.name
            )
        }
    }


    // Detail Movie
    fun mapDetailMovieResponseToEntities(input: DetailMovieResponse): DetailMovieEntity =
        with(input) {
            DetailMovieEntity(
                id = id ?: -1,
                imdbId = imdbId ?: "",
                title = title ?: "",
                originalTitle = originalTitle ?: "",
                originalLanguage = originalLanguage ?: "",
                overview = overview ?: "",
                tagline = tagline ?: "",
                status = status ?: "",
                homepage = homepage ?: "",
                posterPath = posterPath ?: "",
                backdropPath = backdropPath ?: "",
                releaseDate = releaseDate ?: "",
                runtime = runtime ?: 0,
                budget = budget ?: 0,
                revenue = revenue ?: 0,
                popularity = popularity ?: 0.0,
                voteAverage = voteAverage ?: 0.0,
                voteCount = voteCount ?: 0,
                adult = adult ?: false,
                video = video ?: false,
                originCountry = originCountry ?: emptyList(),
                genres = genres ?: emptyList()
            )
        }

    fun DetailMovieEntity.toDomain(): DetailMovie =
        DetailMovie(
            originalLanguage = originalLanguage,
            imdbId = imdbId,
            video = video,
            title = title,
            backdropPath = backdropPath,
            revenue = revenue,
            genres = genres,
            popularity = popularity,
            id = id,
            voteCount = voteCount,
            budget = budget,
            overview = overview,
            originalTitle = originalTitle,
            runtime = runtime,
            posterPath = posterPath,
            originCountry = originCountry,
            releaseDate = releaseDate,
            voteAverage = voteAverage,
            tagline = tagline,
            adult = adult,
            homepage = homepage,
            status = status
        )


    // Casting Movie
    fun mapCastingMovieResponsesToEntities(
        movieId: Int,
        input: List<CastingItem>
    ): List<CastingMovieEntity> {
        return input.map {
            CastingMovieEntity(
                movieId = movieId,
                castId = it.castId,
                character = it.character,
                gender = it.gender,
                creditId = it.creditId,
                knownForDepartment = it.knownForDepartment,
                originalName = it.originalName,
                popularity = it.popularity,
                name = it.name,
                profilePath = it.profilePath,
                id = it.id ?: -1,
                adult = it.adult,
                order = it.order
            )
        }
    }

    fun List<CastingMovieEntity>.toDomain(): List<CastingMovie> {
        return this.map { entity ->
            CastingMovie(
                id = entity.id,
                movieId = entity.movieId,
                castId = entity.castId,
                character = entity.character,
                gender = entity.gender,
                creditId = entity.creditId,
                knownForDepartment = entity.knownForDepartment,
                originalName = entity.originalName,
                popularity = entity.popularity,
                name = entity.name,
                profilePath = entity.profilePath,
                adult = entity.adult,
                order = entity.order
            )
        }
    }


    // Similar Movies
    fun mapSimilarMovieResponsesToEntities(
        movieId: Int,
        input: List<ResultsSimilar>?
    ): List<SimilarMoviesEntity> {
        return input?.map {
            SimilarMoviesEntity(
                movieId = movieId,
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
                id = it.id ?: -1,
                adult = it.adult,
                voteCount = it.voteCount
            )
        } ?: emptyList()
    }

    fun List<SimilarMoviesEntity>.toDomain(allGenresDomain: List<GenresMovie>): List<SimilarMovies> {
        val genreMap = allGenresDomain.associateBy({ it.id }, { it.name })
        return this.map { entity ->
            val genreNames = entity.genreIds?.mapNotNull { id -> genreMap[id] }
            SimilarMovies(
                id = entity.id,
                movieId = entity.movieId,
                overview = entity.overview,
                originalLanguage = entity.originalLanguage,
                originalTitle = entity.originalTitle,
                video = entity.video,
                title = entity.title,
                genreIds = entity.genreIds,
                posterPath = entity.posterPath,
                backdropPath = entity.backdropPath,
                releaseDate = entity.releaseDate,
                popularity = entity.popularity,
                voteAverage = entity.voteAverage,
                adult = entity.adult,
                voteCount = entity.voteCount,
                genreNames = genreNames
            )
        }
    }
}
