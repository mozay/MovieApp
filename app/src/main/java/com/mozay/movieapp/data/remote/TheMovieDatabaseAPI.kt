package com.mozay.movieapp.data.remote

import com.mozay.movieapp.data.model.entity.Movie
import com.mozay.movieapp.data.model.network.MoviesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object TheMovieDatabaseAPI {

    private const val API_VERSION: Int = 3
    private const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/w185"
    private const val BASE_BACKDROP_URL = "https://image.tmdb.org/t/p/w780"
    const val BASE_API_URL = "https://api.themoviedb.org/"
    const val MAX_RATING = 10f

    fun getPosterUrl(path: String) = BASE_POSTER_URL + path
    fun getBackdropUrl(path: String) = BASE_BACKDROP_URL + path
    fun getRuntimeDateFormat() = ("yyyy-MM-dd")

    interface MovieService {
        @GET("/$API_VERSION/movie/popular")
        fun fetchPopularList(@Query("page") page: Int): Call<MoviesResponse>

        @GET("/$API_VERSION/movie/upcoming")
        fun fetchUpcomingList(@Query("page") page: Int): Call<MoviesResponse>

        @GET("/$API_VERSION/movie/now_playing")
        fun fetchInTheatersList(@Query("page") page: Int): Call<MoviesResponse>

        @GET("/$API_VERSION/movie/{id}")
        fun fetchDetails(@Path("id") id: Int): Call<Movie>
    }
}

