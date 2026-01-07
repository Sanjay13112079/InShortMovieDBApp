package com.inshort.moviedb.ui.saved

import com.inshort.moviedb.data.remote.model.Movie

data class SavedUiState(
    val movies : List<Movie> = emptyList(),
    val isEmpty : Boolean = false
)
