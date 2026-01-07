package com.inshort.moviedb.ui.home

import com.inshort.moviedb.data.remote.model.Movie

data class HomeUiState(
    val isLoading: Boolean = false,
    val trendingMovies: List<Movie> = emptyList(),
    val nowPlayingMovies: List<Movie> = emptyList(),
    val errorMessage: String? = null
)

