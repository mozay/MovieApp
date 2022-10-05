package com.mozay.movieapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.mozay.movieapp.common.builder.ServiceBuilder
import com.mozay.movieapp.data.model.entity.Movie
import com.mozay.movieapp.data.remote.TheMovieDatabaseAPI

class MovieRepository : BaseRepository() {
    private val movieService =
        ServiceBuilder.buildService(TheMovieDatabaseAPI.MovieService::class.java)

    suspend fun loadPopularList(page: Int, errorText: (String) -> Unit) =
        loadPageListCall(
            { movieService.fetchPopularList(page) },
            MutableLiveData<List<Movie>>(),
            errorText
        )

    suspend fun loadInTheatersList(page: Int, errorText: (String) -> Unit) =
        loadPageListCall(
            { movieService.fetchInTheatersList(page) },
            MutableLiveData<List<Movie>>(),
            errorText
        )

    suspend fun loadUpcomingList(page: Int, errorText: (String) -> Unit) =
        loadPageListCall(
            { movieService.fetchUpcomingList(page) },
            MutableLiveData<List<Movie>>(),
            errorText
        )

    suspend fun loadDetails(id: Int, errorText: (String) -> Unit) =
        loadCall({ movieService.fetchDetails(id) }, MutableLiveData<Movie>(), errorText)
}
