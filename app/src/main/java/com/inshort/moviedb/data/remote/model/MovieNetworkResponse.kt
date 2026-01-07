package com.inshort.moviedb.data.remote.model

data class MovieNetworkResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
