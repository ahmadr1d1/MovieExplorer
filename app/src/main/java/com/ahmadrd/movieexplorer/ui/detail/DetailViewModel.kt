package com.ahmadrd.movieexplorer.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import com.ahmadrd.movieexplorer.core.data.Resource
import com.ahmadrd.movieexplorer.core.domain.model.CastingMovie
import com.ahmadrd.movieexplorer.core.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    // Trigger untuk movieId yang akan di-observe oleh UI
    private val movieIdLiveData = MutableLiveData<Int>()

    // LiveData hasil konversi Flow dari use case
    val castingMovie: LiveData<Resource<List<CastingMovie>>> =
        movieIdLiveData
            .distinctUntilChanged()
            .switchMap { id ->
                moviesUseCase.getCastingMovie(id).asLiveData()
            }

    fun setMovieId(id: Int) {
        // Hindari emit ulang jika sama
        if (movieIdLiveData.value != id) {
            movieIdLiveData.value = id
        }
    }
}
