package com.mozay.movieapp.data.model.network

import com.google.gson.annotations.SerializedName
import com.mozay.movieapp.data.model.entity.Movie

data class MoviesResponse(
    @SerializedName("page")
    override var page: Int,

    @SerializedName("results")
    override var results: List<Movie>
) : BasePageListResponse<Movie>
