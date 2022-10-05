package com.mozay.movieapp.presentation.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mozay.movieapp.common.util.InfiniteContentScrollListener
import com.mozay.movieapp.data.model.GoToMovie
import com.mozay.movieapp.data.model.entity.Movie
import com.mozay.movieapp.presentation.adapter.MovieListAdapter

@BindingAdapter("bind_movie_list", "bind_load_more", "bind_view_model")
fun RecyclerView.bindMovieList(
    items: List<Movie>?,
    loadMoreContent: () -> Unit,
    goTo: GoToMovie
) {
    if (items == null) return
    if (this.adapter == null) {
        this.adapter =
            MovieListAdapter(goTo, InfiniteContentScrollListener(this) { loadMoreContent() })
    }
    (this.adapter as MovieListAdapter).submitList(items)
}

