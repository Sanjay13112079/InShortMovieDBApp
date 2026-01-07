package com.inshort.moviedb.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inshort.moviedb.data.remote.model.Movie
import com.inshort.moviedb.data.repository.MovieRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            repository.fetchTrendingMoviesAndSaveToDB()
            repository.fetchNowPlayingMoviesAndSaveToDB()
        }
        observeHome()
    }


    private fun observeHome() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }
            combine(
                repository.observeTrendingMovies(),
                repository.observeNowPlayingMovies()
            ) { trending, nowPlaying ->
                HomeUiState(
                    trendingMovies = trending,
                    nowPlayingMovies = nowPlaying,
                    isLoading = false
                )
            }.collect {
                _uiState.value = it
            }
        }
    }

    fun onBookMarkClicked(movie: Movie) {
        viewModelScope.launch {
            repository.updateBookmark(
                movie.id,
                !movie.isBookMarked
            )

            _uiState.update { state ->
                state.copy(
                    trendingMovies = state.trendingMovies.map {
                        if (it.id == movie.id)
                            it.copy(isBookMarked = !movie.isBookMarked)
                        else it
                    },
                    nowPlayingMovies = state.nowPlayingMovies.map {
                        if (it.id == movie.id)
                            it.copy(isBookMarked = !movie.isBookMarked)
                        else it
                    }
                )
            }
        }
    }
}
