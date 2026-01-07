package com.inshort.moviedb.data.local.entity

import androidx.room.Entity

@Entity(
    tableName = "movie_category",
    primaryKeys = ["movieId", "category"]
)
data class MovieCategoryEntity(
    val movieId: Long,
    val category: String
)
