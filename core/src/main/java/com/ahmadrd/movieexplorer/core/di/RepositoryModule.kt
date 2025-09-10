package com.ahmadrd.movieexplorer.core.di

import com.ahmadrd.movieexplorer.core.data.MoviesRepository
import com.ahmadrd.movieexplorer.core.data.source.local.LocalDataSource
import com.ahmadrd.movieexplorer.core.data.source.remote.RemoteDataSource
import com.ahmadrd.movieexplorer.core.domain.repository.IMoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        remote: RemoteDataSource,
        local: LocalDataSource
    ): IMoviesRepository =
        MoviesRepository(remote, local)

}