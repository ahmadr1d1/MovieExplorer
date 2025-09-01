package com.ahmadrd.movieexplorer.core.di

import android.content.Context
import androidx.room.Room
import com.ahmadrd.movieexplorer.core.data.source.local.room.MoviesDatabase
import com.ahmadrd.movieexplorer.core.data.source.local.room.PopularMoviesDao
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

}