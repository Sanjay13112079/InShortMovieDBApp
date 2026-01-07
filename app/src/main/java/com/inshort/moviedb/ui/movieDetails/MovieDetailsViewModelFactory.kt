package com.inshort.moviedb.ui.movieDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inshort.moviedb.data.repository.MovieRepository

class MovieDetailsViewModelFactory(
    val repository: MovieRepository,
    val movieId : Long
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)){
            return MovieDetailsViewModel(repository, movieId) as T
        }
        throw IllegalArgumentException("Unknown ViewModekl")
    }
}