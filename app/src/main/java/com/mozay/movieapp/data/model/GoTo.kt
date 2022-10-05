package com.mozay.movieapp.data.model

import androidx.lifecycle.MutableLiveData
import com.mozay.movieapp.data.model.entity.Movie

interface GoToMovie {
    val goToMovieDetailsEvent: MutableLiveData<Event<Movie>>

    fun goTo(movie: Movie) {
        goToMovieDetailsEvent.value = Event(movie)
    }
}