package com.ahmadrd.movieexplorer.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ahmadrd.movieexplorer.core.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(moviesUseCase: MoviesUseCase) : ViewModel() {
    val popularMovies = moviesUseCase.getPopularMovies().asLiveData()
}