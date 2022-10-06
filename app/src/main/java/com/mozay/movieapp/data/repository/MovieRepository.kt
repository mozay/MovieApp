package com.mozay.movieapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.mozay.movieapp.data.model.entity.Movie
import com.mozay.movieapp.data.model.entity.Video
import com.mozay.movieapp.data.remote.TheMovieDatabaseAPI
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieService: TheMovieDatabaseAPI.MovieService) : BaseRepository() {

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


    suspend fun loadVideos(id: Int, errorText: (String) -> Unit) =
        loadListCall(
            { movieService.fetchVideos(id) },
            MutableLiveData<List<Video>>(),
            errorText
        )
}
