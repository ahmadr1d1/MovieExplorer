package com.ahmadrd.movieexplorer.core.utils

import com.ahmadrd.movieexplorer.core.data.source.local.entity.GenresMovieEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.PopularMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.TrendingMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.remote.response.GenresItem
import com.ahmadrd.movieexplorer.core.data.source.remote.response.ResultsItem
import com.ahmadrd.movieexplorer.core.data.source.remote.response.ResultsTrendingMovies
import com.ahmadrd.movieexplorer.core.domain.model.GenresMovie
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

    fun mapPMoviesEntitiesToDomain(
        input: List<PopularMoviesEntity>,
        allGenresDomain: List<GenresMovie>
    ): List<PopularMovies> {
        val genreMap = allGenresDomain.associateBy({ it.id }, { it.name })
        return input.map { entity ->
            val genreNames = entity.genreIds.mapNotNull { id -> genreMap[id] }
            PopularMovies(
                id = entity.id,
                title = entity.title,
                overview = entity.overview,
                originalLanguage = entity.originalLanguage,
                originalTitle = entity.originalTitle,
                video = entity.video,
                genreIds = entity.genreIds, // Tetap simpan List<Int> jika masih dibutuhkan
                genreNames = genreNames,    // List<String> yang sudah di-map
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
            isFavorite = input.isFavorite
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


    fun mapTMoviesEntitiesToDomain(
        input: List<TrendingMoviesEntity>,
        allGenresDomain: List<GenresMovie> // Daftar semua genre (ID + Nama)
    ): List<TrendingMovies> {
        val genreMap = allGenresDomain.associateBy({ it.id }, { it.name })
        return input.map { entity ->
            val genreNames = entity.genreIds.mapNotNull { id -> genreMap[id] }
            TrendingMovies(
                id = entity.id,
                title = entity.title,
                overview = entity.overview,
                originalLanguage = entity.originalLanguage,
                originalTitle = entity.originalTitle,
                video = entity.video,
                genreIds = entity.genreIds, // Tetap simpan List<Int>
                genreNames = genreNames,    // List<String> yang sudah di-map
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

    // Mapper dari TrendingMovies (Domain) ke PopularMoviesEntity (untuk disimpan sebagai favorit)
    fun mapTrendingDomainToPopularEntity(input: TrendingMovies): PopularMoviesEntity =
        PopularMoviesEntity(
            id = input.id,
            title = input.title,
            overview = input.overview,
            originalLanguage = input.originalLanguage,
            originalTitle = input.originalTitle,
            video = input.video,
            genreIds = input.genreIds, // Ambil dari TrendingMovies Domain
            posterPath = input.posterPath ?: "",
            backdropPath = input.backdropPath ?: "",
            releaseDate = input.releaseDate ?: "N/A",
            popularity = input.popularity,
            voteAverage = input.voteAverage,
            adult = input.adult,
            voteCount = input.voteCount,
            isFavorite = input.isFavorite
        )

    // --- Genres Movie Mappers ---
    fun mapGenreResponsesToEntities(input: List<GenresItem>): List<GenresMovieEntity> {
        return input.map {
            GenresMovieEntity(
                id = it.id,
                name = it.name
            )
        }
    }

    fun mapGenreEntitiesToDomain(input: List<GenresMovieEntity>): List<GenresMovie> {
        return input.map {
            GenresMovie(
                id = it.id,
                name = it.name
            )
        }
    }
}