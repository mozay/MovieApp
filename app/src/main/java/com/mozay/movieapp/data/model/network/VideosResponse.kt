package com.mozay.movieapp.data.model.network

import com.google.gson.annotations.SerializedName
import com.mozay.movieapp.data.model.entity.Video

data class VideosResponse(
    @SerializedName("results")
    override var results: List<Video>
) : BaseListResponse<Video>