package com.inshort.moviedb.ui.movieDetails

import com.inshort.moviedb.data.remote.model.MovieDetailResponse

data class MovieDetailUiState(
    val isLoading: Boolean = true,
    val movieDetail: MovieDetailResponse? = null,
    val errorMessage: String? = null
)
