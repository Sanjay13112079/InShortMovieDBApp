package com.inshort.moviedb.data.remote.model

data class MovieDetailResponse(
    val id: Long,
    val title: String,
    val tagline: String?,
    val overview: String,
    val popularity : Double,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String,
    val runtime: Int?,
    val vote_average: Double,
    val status: String,
    val homepage: String?,
    val genres: List<GenreDto>,
    val isBookMarked: Boolean
)

data class GenreDto(
    val id: Int,
    val name: String
)