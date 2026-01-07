package com.inshort.moviedb.ui.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inshort.moviedb.data.remote.model.Movie
import com.inshort.moviedb.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SavedViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SavedUiState())
    val uiState: StateFlow<SavedUiState> = _uiState

    init {
        loadSavedMovies()
    }

    private fun loadSavedMovies() {
        viewModelScope.launch {
            repository.getBookmarkedMovies()
                .collect { movies ->
                    _uiState.update {
                        it.copy(movies = movies, isEmpty = movies.isEmpty())
                    }
                }
        }
    }

    fun onBookMarkClicked(movie: Movie) {
        viewModelScope.launch {
            repository.updateBookmark(
                movie.id,
                !movie.isBookMarked
            )
        }
        loadSavedMovies()
    }
}
