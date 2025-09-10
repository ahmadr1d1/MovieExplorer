package com.ahmadrd.movieexplorer.di

import com.ahmadrd.movieexplorer.core.domain.usecase.MoviesInteractor
import com.ahmadrd.movieexplorer.core.domain.usecase.MoviesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // sekarang Singleton
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideMoviesUseCase(
        moviesInteractor: MoviesInteractor
    ): MoviesUseCase
}