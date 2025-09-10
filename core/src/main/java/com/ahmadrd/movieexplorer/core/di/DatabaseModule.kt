package com.ahmadrd.movieexplorer.core.di

import android.content.Context
import androidx.room.Room
import com.ahmadrd.movieexplorer.core.data.source.local.room.CastingMovieDao
import com.ahmadrd.movieexplorer.core.data.source.local.room.DetailMovieDao
import com.ahmadrd.movieexplorer.core.data.source.local.room.FavoriteDao
import com.ahmadrd.movieexplorer.core.data.source.local.room.GenresMovieDao
import com.ahmadrd.movieexplorer.core.data.source.local.room.MoviesDatabase
import com.ahmadrd.movieexplorer.core.data.source.local.room.PopularMoviesDao
import com.ahmadrd.movieexplorer.core.data.source.local.room.SimilarMoviesDao
import com.ahmadrd.movieexplorer.core.data.source.local.room.TrendingMoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MoviesDatabase =
        Room.databaseBuilder(
            context,
            MoviesDatabase::class.java, "movies.db"
        ).fallbackToDestructiveMigration(true).build()

    @Singleton
    @Provides
    fun providePopularMoviesDao(database: MoviesDatabase): PopularMoviesDao =
        database.popularMoviesDao()

    @Singleton
    @Provides
    fun provideTrendingMoviesDao(database: MoviesDatabase): TrendingMoviesDao =
        database.trendingMoviesDao()

    @Singleton
    @Provides
    fun provideGenresMovieDao(database: MoviesDatabase): GenresMovieDao =
        database.genresMovieDao()

    @Singleton
    @Provides
    fun provideDetailMovieDao(database: MoviesDatabase): DetailMovieDao =
        database.detailMovieDao()

    @Singleton
    @Provides
    fun provideCastingMovieDao(database: MoviesDatabase): CastingMovieDao =
        database.castingMovieDao()

    @Singleton
    @Provides
    fun provideSimilarMoviesDao(database: MoviesDatabase): SimilarMoviesDao =
        database.similarMoviesDao()

    @Singleton
    @Provides
    fun provideFavoriteDao(database: MoviesDatabase): FavoriteDao =
        database.favoriteDao()

}