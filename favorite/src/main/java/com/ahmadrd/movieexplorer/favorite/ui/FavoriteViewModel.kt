package com.ahmadrd.movieexplorer.favorite.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.ahmadrd.movieexplorer.core.domain.model.AllMovie
import com.ahmadrd.movieexplorer.core.domain.usecase.MoviesUseCase
import com.ahmadrd.movieexplorer.core.utils.favorites.FavoriteState
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    // Trigger refresh
    private val refreshTrigger = MutableLiveData(Unit)

    val favoriteMovies = refreshTrigger.switchMap {
        moviesUseCase.getFavorites().map { movies ->
            if (movies.isEmpty()) {
                FavoriteState.Empty
            } else {
                FavoriteState.Success(movies)
            }
        }.asLiveData()
    }

    fun removeMovieFromFavorite(movie: AllMovie) {
        viewModelScope.launch {
            moviesUseCase.removeFavorite(movie)
            retry() // auto refresh after deleting item
        }
    }

    fun retry() {
        refreshTrigger.value = Unit
    }
}
