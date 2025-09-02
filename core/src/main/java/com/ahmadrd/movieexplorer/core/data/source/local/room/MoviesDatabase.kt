package com.ahmadrd.movieexplorer.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ahmadrd.movieexplorer.core.data.source.local.entity.GenresMovieEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.PopularMoviesEntity
import com.ahmadrd.movieexplorer.core.data.source.local.entity.TrendingMoviesEntity
import com.ahmadrd.movieexplorer.core.utils.Converters

@Database(entities = [PopularMoviesEntity::class,
    TrendingMoviesEntity::class,
    GenresMovieEntity::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun popularMoviesDao(): PopularMoviesDao

    abstract fun trendingMoviesDao(): TrendingMoviesDao

    abstract fun genresMovieDao(): GenresMovieDao

}