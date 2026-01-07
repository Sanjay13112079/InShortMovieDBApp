package com.inshort.moviedb.data.repository

import com.inshort.moviedb.data.remote.model.Movie
import com.inshort.moviedb.data.remote.model.MovieDetailResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun observeTrendingMovies():  Flow<List<Movie>>
    suspend fun observeNowPlayingMovies():  Flow<List<Movie>>

    suspend fun fetchTrendingMoviesAndSaveToDB()
    suspend fun fetchNowPlayingMoviesAndSaveToDB()

    suspend fun getBookmarkedMovies(): Flow<List<Movie>>
    suspend fun updateBookmark(movieId: Long, bookmarked: Boolean)
    suspend fun updateBookmarkFromSearch(movie: Movie, bookmarked: Boolean)
    suspend fun searchMovies(query: String): List<Movie>
    suspend fun getMovieDetails(movieId: Long): MovieDetailResponse
    suspend fun getMovieById(movieId: Long) : Movie?
}