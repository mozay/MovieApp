package com.mozay.movieapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mozay.movieapp.common.extensions.liveDataBlockScope
import com.mozay.movieapp.data.model.Event
import com.mozay.movieapp.data.model.entity.Movie
import com.mozay.movieapp.data.repository.MovieRepository
import com.mozay.movieapp.presentation.base.BaseViewModel

class MovieDetailsViewModelFactory(private val movieId: Int) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailsViewModel(movieId) as T
    }
}

class MovieDetailsViewModel(movieId: Int) : BaseViewModel() {

    private val movieRepository = MovieRepository()
    val movie: LiveData<Movie>

    init {
        movie = liveDataBlockScope {
            movieRepository.loadDetails(movieId) { mSnackBarText.postValue(Event(it)) }
        }
    }
}
