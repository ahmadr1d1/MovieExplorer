package com.ahmadrd.movieexplorer.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.ahmadrd.movieexplorer.core.R
import com.ahmadrd.movieexplorer.core.data.Resource
import com.ahmadrd.movieexplorer.core.domain.model.AllMovie
import com.ahmadrd.movieexplorer.core.domain.model.CastingMovie
import com.ahmadrd.movieexplorer.core.domain.model.DetailMovie
import com.ahmadrd.movieexplorer.core.domain.model.SimilarMovies
import com.ahmadrd.movieexplorer.core.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase,
    private val application: Application
) : ViewModel() {

    // Trigger for movieId that will be observed by UI
    private val movieIdLiveData = MutableLiveData<Int>()

    // Flag Toast
    private val _toastMessage = MutableLiveData<String?>()
    val toastMessage: LiveData<String?> = _toastMessage

    val detailMovie: LiveData<Resource<DetailMovie>> =
        movieIdLiveData
            .distinctUntilChanged()
            .switchMap { id ->
                moviesUseCase.getDetailMovie(id).asLiveData()
            }

    val castingMovie: LiveData<Resource<List<CastingMovie>>> =
        movieIdLiveData
            .distinctUntilChanged()
            .switchMap { id ->
                moviesUseCase.getCastingMovie(id).asLiveData()
            }

    val similarMovies: LiveData<Resource<List<SimilarMovies>>> =
        movieIdLiveData
            .distinctUntilChanged()
            .switchMap { id ->
                moviesUseCase.getSimilarMovies(id).asLiveData()
            }

    // State favorite
    val isFavorite: LiveData<Boolean> =
        movieIdLiveData
            .distinctUntilChanged()
            .switchMap { id ->
                moviesUseCase.isFavorite(id).asLiveData()
            }

    // Toggle favorite
    fun toggleFavorite(allMovie: AllMovie) {
        viewModelScope.launch {
            val currentState = isFavorite.value ?: false
            val newState = !currentState

            // Update database
            moviesUseCase.setFavorite(allMovie, newState)

            _toastMessage.value = if (newState) {
                application.getString(R.string.success_added)
            } else {
                application.getString(R.string.success_removed)
            }
        }
    }

    // Clear toast message after showing up
    fun clearToastMessage() {
        _toastMessage.value = null
    }

    fun retry() {
        movieIdLiveData.value?.let { currentId ->
            // retrigger with the same value
            movieIdLiveData.value = currentId
        }
    }

    fun setMovieId(id: Int) {
        if (movieIdLiveData.value != id) {
            movieIdLiveData.value = id
            // Clear toast message untuk movie baru
            _toastMessage.value = null
        }
    }
}
