package com.mozay.movieapp.presentation.binding

import android.widget.RatingBar
import androidx.databinding.BindingAdapter
import com.mozay.movieapp.data.model.entity.Movie
import com.mozay.movieapp.data.remote.TheMovieDatabaseAPI

@BindingAdapter("bind_rating_bar", "bind_rating_stars")
fun RatingBar.bindRatingBar(movie: Movie?, stars: Int) {
    movie?.let { this.rating = stars * ((it.voteAverage / TheMovieDatabaseAPI.MAX_RATING)) }
}