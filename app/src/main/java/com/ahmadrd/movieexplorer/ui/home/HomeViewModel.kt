package com.ahmadrd.movieexplorer.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.ahmadrd.movieexplorer.core.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    // Trigger refresh
    private val refreshTrigger = MutableLiveData(Unit)

    val popularMovies = refreshTrigger.switchMap {
        moviesUseCase.getPopularMovies().asLiveData()
    }

    val trendingMovies = refreshTrigger.switchMap {
        moviesUseCase.getTrendingMovies().asLiveData()
    }

    val genresMovie = refreshTrigger.switchMap {
        moviesUseCase.getGenresMovie().asLiveData()
    }

    fun refresh() {
        refreshTrigger.value = Unit // re-trigger all flow in this clas
    }
}
