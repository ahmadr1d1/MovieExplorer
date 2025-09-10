package com.ahmadrd.movieexplorer.core.utils.favorites

sealed class FavoriteState<out T> {
    object Loading : FavoriteState<Nothing>()
    data class Success<out T>(val data: T) : FavoriteState<T>()
    object Empty : FavoriteState<Nothing>()
    data class Error(val message: String) : FavoriteState<Nothing>()
}