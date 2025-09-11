package com.ahmadrd.movieexplorer.core.utils

import android.util.Log
import com.ahmadrd.movieexplorer.core.data.source.local.entity.CastingMovieEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.DetailMovieEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.FavoriteEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.GenresMovieEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.PopularMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.SimilarMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.TrendingMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.remote.response.CastingItem
import com.ahmadrd.movieexplorer.core.data.source.remote.response.DetailMovieResponse
import com.ahmadrd.movieexplorer.core.data.source.remote.response.GenresItem
import com.ahmadrd.movieexplorer.core.data.source.remote.response.ResultsItem
import com.ahmadrd.movieexplorer.core.data.source.remote.response.ResultsSimilar
import com.ahmadrd.movieexplorer.core.data.source.remote.response.ResultsTrendingMovies
import com.ahmadrd.movieexplorer.core.domain.model.AllMovie
import com.ahmadrd.movieexplorer.core.domain.model.CastingMovie
import com.ahmadrd.movieexplorer.core.domain.model.DetailMovie
import com.ahmadrd.movieexplorer.core.domain.model.GenresMovie
import com.ahmadrd.movieexplorer.core.domain.model.PopularMovies
import com.ahmadrd.movieexplorer.core.domain.model.SimilarMovies
import com.ahmadrd.movieexplorer.core.domain.model.TrendingMovies
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DataMapper {

    private const val TAG = "DataMapper"

    // Popular Movies
    fun mapPopularMoviesResponsesToEntities(input: List<ResultsItem>?): List<PopularMoviesEntity> {
        return input?.mapNotNull {
            if (it.id == null || it.id <= 0) {
                Log.w(TAG, "Popular movie with invalid ID (${it.id}) skipped: ${it.title}")
                null
            } else {
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
                    id = it.id, // ID is now non-null and > 0
                    adult = it.adult,
                    voteCount = it.voteCount
                )
            }
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
                voteCount = entity.voteCount
            )
        }
    }


    // Trending Movies
    fun mapTrendingMoviesResponsesToEntities(
        input: List<ResultsTrendingMovies>?
    ): List<TrendingMoviesEntity> {
        return input?.mapNotNull {
            if (it.id == null || it.id <= 0) {
                Log.w(TAG, "Trending movie with invalid ID (${it.id}) skipped: ${it.title}")
                null
            } else {
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
                    id = it.id, // ID is now non-null and > 0
                    adult = it.adult,
                    voteCount = it.voteCount
                )
            }
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
                voteCount = entity.voteCount
            )
        }
    }


    // Genres Movie
    fun mapGenreResponsesToEntities(input: List<GenresItem>?): List<GenresMovieEntity> {
        return input?.mapNotNull {
            if (it.id == null || it.id <= 0) { // Also ensure genre IDs are valid
                Log.w(TAG, "Genre with invalid ID (${it.id}) skipped: ${it.name}")
                null
            } else {
                GenresMovieEntity(
                    id = it.id,
                    name = it.name
                )
            }
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
        // For detail, we still map it, but toAllMovie() will be the gatekeeper for favoriting
        with(input) {
            DetailMovieEntity(
                id = id ?: -1, // Keep as is, toAllMovie will check
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
                genres = genres?.mapNotNull { if (it.id == null || it.id <=0) null else it } ?: emptyList() // Filter invalid genres here too
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
            // Ensure genres in DetailMovie are also from valid GenreItem
            genres = genres.map { GenresItem(name = it.name, id = it.id) },
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
        return input.mapNotNull {
            if (it.id == null || it.id <= 0) {
                Log.w(TAG, "Casting item with invalid ID (${it.id}) for movie ID $movieId skipped: ${it.name}")
                null
            } else {
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
                    id = it.id,
                    adult = it.adult,
                    order = it.order
                )
            }
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
        return input?.mapNotNull {
            if (it.id == null || it.id <= 0) {
                Log.w(TAG, "Similar movie with invalid ID (${it.id}) for movie ID $movieId skipped: ${it.title}")
                null
            } else {
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
                    id = it.id,
                    adult = it.adult,
                    voteCount = it.voteCount
                )
            }
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


    // Favorites
    fun List<FavoriteEntity>.favoriteToDomain(allGenresDomain: List<GenresMovie>): List<AllMovie> {
        val genreMap = allGenresDomain.associateBy({ it.id }, { it.name })
        return this.mapNotNull { entity -> // mapNotNull to filter out any potentially invalid old entries
            if (entity.id <= 0) { // Check if ID is valid (positive)
                 Log.w(TAG, "Favorite movie with invalid ID (${entity.id}) skipped from domain mapping: ${entity.title}")
                null
            } else {
                val genreNames = entity.genreIds?.mapNotNull { id -> genreMap[id] }
                AllMovie(
                    id = entity.id,
                    title = entity.title,
                    posterPath = entity.posterPath,
                    releaseDate = entity.releaseDate,
                    voteAverage = entity.voteAverage,
                    genreIds = entity.genreIds,
                    overview = entity.overview,
                    originalLanguage = entity.originalLanguage,
                    runtime = entity.runtime,
                    dateAdded = entity.dateAdded,
                    genreNames = genreNames
                )
            }
        }
    }

    // Convert AllMovie (domain) to FavoriteEntity (database)
    fun AllMovie.favoriteToEntity(): FavoriteEntity = FavoriteEntity(
        id = this.id,
        title = title,
        posterPath = posterPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        genreIds = genreIds,
        overview = overview,
        originalLanguage = originalLanguage,
        runtime = runtime,
        dateAdded = dateAdded
    )

    // Convert DetailMovie (domain) to AllMovie (domain) for favoriting
    fun DetailMovie.toAllMovie(): AllMovie? {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val currentDate = sdf.format(Date())
        return AllMovie(
            id = this.id,
            overview = this.overview,
            originalLanguage = this.originalLanguage,
            title = this.title,
            genreIds = this.genres?.mapNotNull { it.id },
            genreNames = this.genres?.mapNotNull { it.name },
            posterPath = this.posterPath,
            releaseDate = this.releaseDate,
            voteAverage = this.voteAverage,
            runtime = this.runtime,
            dateAdded = currentDate
        )
    }
}
