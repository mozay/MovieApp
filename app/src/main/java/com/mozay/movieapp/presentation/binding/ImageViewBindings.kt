package com.mozay.movieapp.presentation.binding

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.mozay.movieapp.R
import com.mozay.movieapp.data.remote.TheMovieDatabaseAPI
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.lang.Exception

@BindingAdapter("bind_backdrop_path_loading", "bind_progress")
fun ImageView.bindBackdropImageWithPicassoLoading(path: String?, progressBar: ProgressBar) {
    if (path.isNullOrBlank()) {
        this.setImageResource(R.drawable.ic_error_image)
        return
    }
    progressBar.visibility = View.VISIBLE
    Picasso.get().load(TheMovieDatabaseAPI.getBackdropUrl(path)).fit()
        .transform(RoundedCornersTransformation(4, 1))
        .error(R.drawable.ic_error_image)
        .into(this, object : Callback {
            override fun onSuccess() {
                progressBar.visibility = View.GONE
            }

            override fun onError(e: Exception?) {
                progressBar.visibility = View.GONE
            }
        })
}

@BindingAdapter("bind_poster_path")
fun ImageView.bindPosterImageWithPicasso(path: String?) {
    if (path.isNullOrBlank()) {
        this.setImageResource(R.drawable.ic_error_image)
        return
    }
    Picasso.get().load(TheMovieDatabaseAPI.getPosterUrl(path)).fit()
        .transform(RoundedCornersTransformation(4, 1))
        .error(R.drawable.ic_error_image).into(this)
}

