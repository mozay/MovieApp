package com.mozay.movieapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mozay.movieapp.common.extensions.liveDataBlockScope
import com.mozay.movieapp.data.model.Event
import com.mozay.movieapp.data.model.entity.Movie
import com.mozay.movieapp.data.repository.MovieRepository
import com.mozay.movieapp.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel@Inject constructor(
    private val movieRepository: MovieRepository
) : BaseViewModel() {

    lateinit var movie: LiveData<Movie>

    fun getMovieDetail(movieId: Int){
        movie = liveDataBlockScope {
            movieRepository.loadDetails(movieId) { mSnackBarText.postValue(Event(it)) }
        }
    }
}
