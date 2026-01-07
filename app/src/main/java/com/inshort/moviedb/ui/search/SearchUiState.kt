package com.inshort.moviedb.ui.search

import com.inshort.moviedb.data.remote.model.Movie

data class SearchUiState(
    val query: String = "",
    val results: List<Movie> = emptyList(),
    val isLoading: Boolean = false
)