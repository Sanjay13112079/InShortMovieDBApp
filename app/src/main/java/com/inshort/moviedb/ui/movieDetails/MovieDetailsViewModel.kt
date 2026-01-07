package com.inshort.moviedb.ui.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inshort.moviedb.data.mappers.toMovie
import com.inshort.moviedb.data.mappers.toMovieDetailResponse
import com.inshort.moviedb.data.remote.model.Movie
import com.inshort.moviedb.data.remote.model.MovieDetailResponse
import com.inshort.moviedb.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val repository: MovieRepository,
    private val movieId: Long
) : ViewModel() {

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState


    init {
        loadMovieDetails(movieId)
    }

    private fun loadMovieDetails(movieId: Long) {
        viewModelScope.launch {

            val localMovie = repository.getMovieById(movieId)
            if (localMovie != null) {
                _uiState.update {
                    it.copy(
                        movieDetail = localMovie.toMovieDetailResponse(),
                        isLoading = false
                    )
                }
            }

            try {
                val movieDetails = repository.getMovieDetails(movieId)
                _uiState.value = MovieDetailUiState(
                    isLoading = false,
                    movieDetail = movieDetails.copy(
                        isBookMarked = localMovie?.isBookMarked ?: false
                    )
                )
            } catch (e: Exception) {
                if (localMovie == null) {
                    _uiState.value = MovieDetailUiState(
                        isLoading = false,
                        errorMessage = e.message ?: "Something went wrong"
                    )
                }
            }
        }
    }

    fun onBookMarkClicked(movieDetail: MovieDetailResponse) {
        _uiState.update { state ->
            state.copy(
                movieDetail = state.movieDetail?.copy(
                    isBookMarked = !movieDetail.isBookMarked
                )
            )
        }

        viewModelScope.launch {
            repository.updateBookmarkFromSearch(
                movieDetail.toMovie(),
                !movieDetail.isBookMarked
            )
        }
    }


}