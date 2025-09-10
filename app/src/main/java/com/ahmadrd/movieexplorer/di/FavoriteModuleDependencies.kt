package com.ahmadrd.movieexplorer.di

import com.ahmadrd.movieexplorer.core.domain.usecase.MoviesUseCase
import dagger.hilt.*
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    fun moviesUseCase(): MoviesUseCase
}