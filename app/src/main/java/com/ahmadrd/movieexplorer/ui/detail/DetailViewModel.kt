package com.ahmadrd.movieexplorer.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import com.ahmadrd.movieexplorer.core.data.Resource
import com.ahmadrd.movieexplorer.core.domain.model.CastingMovie
import com.ahmadrd.movieexplorer.core.domain.model.DetailMovie
import com.ahmadrd.movieexplorer.core.domain.model.SimilarMovies
import com.ahmadrd.movieexplorer.core.domain.usecase.MoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase
) : ViewModel() {

    // Trigger for movieId that will be observed by UI
    private val movieIdLiveData = MutableLiveData<Int>()


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

    fun setMovieId(id: Int) {
        // Avoid re-emitting if it is the same
        if (movieIdLiveData.value != id) {
            movieIdLiveData.value = id
        }
    }
}
