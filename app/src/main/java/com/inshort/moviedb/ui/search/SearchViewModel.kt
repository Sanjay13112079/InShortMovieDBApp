package com.inshort.moviedb.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inshort.moviedb.data.remote.model.Movie
import com.inshort.moviedb.data.repository.MovieRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel(val repository: MovieRepository) : ViewModel() {

    private val currentQueryFlow = MutableStateFlow("")
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    fun onQueryChanged(query: String) {
        _uiState.value = _uiState.value.copy(query = query)
        currentQueryFlow.value = query
    }


    init {
        searchMovies()
    }

    @OptIn(FlowPreview::class)
    private fun searchMovies() {
        viewModelScope.launch {

            currentQueryFlow
                .debounce(800)
                .distinctUntilChanged()
                .collectLatest { query ->
                    _uiState.value = _uiState.value.copy(isLoading = true)
                    val result = repository.searchMovies(query)
                    _uiState.value = _uiState.value.copy(
                        results = result,
                        isLoading = false
                    )
                }
        }
    }

    fun onBookMarkClicked(movie: Movie) {
        _uiState.update { state ->
            state.copy(
                results = state.results.map {
                    if (it.id == movie.id)
                        it.copy(isBookMarked = !movie.isBookMarked)
                    else it
                }
            )
        }

        viewModelScope.launch {
            repository.updateBookmarkFromSearch(
                movie,
                !movie.isBookMarked
            )
        }
    }
}