package com.inshort.moviedb.ui.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.inshort.moviedb.data.repository.MovieRepository

class SavedViewModelFactory(
    val repository: MovieRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedViewModel::class.java)) {
            return SavedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown Model")
    }
}