package com.ahmadrd.movieexplorer.favorite.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmadrd.movieexplorer.core.domain.usecase.MoviesUseCase
import com.ahmadrd.movieexplorer.favorite.ui.FavoriteViewModel
import javax.inject.Inject

class FavoriteViewModelFactory @Inject constructor(private val moviesUseCase: MoviesUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(moviesUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}