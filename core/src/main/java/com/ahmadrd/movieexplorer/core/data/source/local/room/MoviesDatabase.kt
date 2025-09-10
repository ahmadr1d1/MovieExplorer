package com.ahmadrd.movieexplorer.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ahmadrd.movieexplorer.core.data.source.local.entity.CastingMovieEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.DetailMovieEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.FavoriteEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.GenresMovieEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.PopularMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.SimilarMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.TrendingMoviesEntity
import com.ahmadrd.movieexplorer.core.utils.database.Converters

@Database(entities = [PopularMoviesEntity::class,
    TrendingMoviesEntity::class,
    GenresMovieEntity::class,
    DetailMovieEntity::class,
    CastingMovieEntity::class,
    SimilarMoviesEntity::class,
    FavoriteEntity::class],
    version = 12,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun popularMoviesDao(): PopularMoviesDao

    abstract fun trendingMoviesDao(): TrendingMoviesDao

    abstract fun genresMovieDao(): GenresMovieDao

    abstract fun detailMovieDao(): DetailMovieDao

    abstract fun castingMovieDao(): CastingMovieDao

    abstract fun similarMoviesDao(): SimilarMoviesDao

    abstract fun favoriteDao(): FavoriteDao

}