package com.mozay.movieapp.data.model.network

interface BasePageListResponse<T> {
    var page: Int
    var results: List<T>
}