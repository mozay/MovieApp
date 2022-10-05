package com.mozay.movieapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import com.mozay.movieapp.common.extensions.appendList
import com.mozay.movieapp.common.extensions.liveDataBlockScope
import com.mozay.movieapp.data.model.Event
import com.mozay.movieapp.data.model.GoToMovie
import com.mozay.movieapp.data.model.entity.Movie
import com.mozay.movieapp.data.repository.MovieRepository
import com.mozay.movieapp.presentation.base.BaseViewModel


class HomeViewModel : BaseViewModel(), GoToMovie {

    private val movieRepository = MovieRepository()

    private val loadedPopularMovieList: LiveData<List<Movie>>
    private val loadedInTheatersMovieList: LiveData<List<Movie>>
    private val loadedUpcomingMovieList: LiveData<List<Movie>>

    private val popularPage = MutableLiveData<Int>().apply { value = 1 }
    private val inTheatersPage = MutableLiveData<Int>().apply { value = 1 }
    private val upcomingPage = MutableLiveData<Int>().apply { value = 1 }


    val popularMovieList = MediatorLiveData<MutableList<Movie>>()
    val inTheatersMovieList = MediatorLiveData<MutableList<Movie>>()
    val upcomingMovieList = MediatorLiveData<MutableList<Movie>>()

    override val goToMovieDetailsEvent: MutableLiveData<Event<Movie>> = MutableLiveData()

    init {
        loadedPopularMovieList = popularPage.switchMap {
            liveDataBlockScope {
                movieRepository.loadPopularList(it) { mSnackBarText.postValue(Event(it)) }
            }
        }

        loadedInTheatersMovieList = inTheatersPage.switchMap {
            liveDataBlockScope {
                movieRepository.loadInTheatersList(it) { mSnackBarText.postValue(Event(it)) }
            }
        }

        loadedUpcomingMovieList = upcomingPage.switchMap {
            liveDataBlockScope {
                movieRepository.loadUpcomingList(it) { mSnackBarText.postValue(Event(it)) }
            }
        }

        popularMovieList.addSource(loadedPopularMovieList) {
            it?.let { list -> popularMovieList.appendList(list) }
        }

        inTheatersMovieList.addSource(loadedInTheatersMovieList) {
            it?.let { list -> inTheatersMovieList.appendList(list) }
        }

        upcomingMovieList.addSource(loadedUpcomingMovieList) {
            it?.let { list -> upcomingMovieList.appendList(list) }
        }
    }

    fun loadMorePopular() {
        popularPage.value = popularPage.value?.plus(1)
    }

    fun loadMoreInTheaters() {
        inTheatersPage.value = inTheatersPage.value?.plus(1)
    }

    fun loadMoreUpcoming() {
        upcomingPage.value = upcomingPage.value?.plus(1)
    }

    override fun goTo(movie: Movie) {
        goToMovieDetailsEvent.value = Event(movie)
    }
}
