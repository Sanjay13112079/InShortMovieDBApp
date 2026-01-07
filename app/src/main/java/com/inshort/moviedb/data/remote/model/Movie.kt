package com.inshort.moviedb.data.remote.model

data class Movie(
    val backdrop_path: String?,
    val id: Long,
    val title: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val poster_path: String,
    val media_type: String?,
    val genre_ids: List<Int>?,
    val popularity: Double,
    val release_date: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val isBookMarked : Boolean
)
