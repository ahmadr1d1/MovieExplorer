package com.ahmadrd.movieexplorer.favorite.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ahmadrd.movieexplorer.core.domain.model.AllMovie
import com.ahmadrd.movieexplorer.core.domain.usecase.MoviesUseCase
import com.ahmadrd.movieexplorer.core.utils.favorites.FavoriteState
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    val favoriteMovies = moviesUseCase.getFavorites().map { movies ->
        if (movies.isEmpty()) {
            FavoriteState.Empty
        } else {
            FavoriteState.Success(movies)
        }
    }.asLiveData()

    fun removeMovieFromFavorite(movie: AllMovie) {
        viewModelScope.launch {
            moviesUseCase.removeFavorite(movie)
        }
    }
}