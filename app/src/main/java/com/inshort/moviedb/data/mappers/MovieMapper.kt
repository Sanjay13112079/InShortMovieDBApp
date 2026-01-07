package com.inshort.moviedb.data.mappers

import com.inshort.moviedb.data.local.entity.MovieEntity
import com.inshort.moviedb.data.remote.model.Movie
import com.inshort.moviedb.data.remote.model.MovieDetailResponse

fun Movie.toEntity(bookMarked: Boolean = false): MovieEntity {
    return MovieEntity(
        id = id,
        title = title ?: "",
        overview = overview ?: "",
        posterPath = poster_path,
        backdropPath = backdrop_path,
        releaseDate = release_date,
        rating = vote_average,
        popularity = popularity,
        isBookmarked = bookMarked
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        poster_path = posterPath ?: "",
        backdrop_path = backdropPath ?: "",
        release_date = releaseDate ?: "",
        popularity = popularity,
        vote_average = rating ?: 0.0,
        vote_count = 0,
        original_language = "",
        original_title = title,
        media_type = "",
        genre_ids = emptyList(),
        video = false,
        isBookMarked = isBookmarked
    )


}


fun Movie.toMovieDetailResponse(): MovieDetailResponse {
    return MovieDetailResponse(
        id = id,
        title = title ?: "",
        tagline = null,
        popularity = popularity,
        overview = overview ?: "",
        poster_path = poster_path,
        backdrop_path = backdrop_path,
        release_date = release_date,
        runtime = 0,
        vote_average = 0.0,
        status = "",
        homepage = null,
        genres = emptyList(),
        isBookMarked = isBookMarked
    )
}

fun MovieDetailResponse.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        poster_path = poster_path ?: "",
        backdrop_path = backdrop_path ?: "",
        release_date = release_date ?: "",
        popularity = popularity,
        vote_average = vote_average ?: 0.0,
        vote_count = 0,
        original_language = "",
        original_title = title,
        media_type = "",
        genre_ids = emptyList(),
        video = false,
        isBookMarked = isBookMarked
    )
}

