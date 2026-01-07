package com.inshort.moviedb.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.inshort.moviedb.data.local.entity.MovieEntity
import com.inshort.moviedb.data.local.entity.MovieCategoryEntity
import com.inshort.moviedb.data.remote.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieCategory(categoryList: List<MovieCategoryEntity>)

    @Query(
        """SELECT m.* FROM movies m INNER JOIN movie_category mc ON m.id = mc.movieId
    WHERE mc.category = :category"""
    )
    fun getMoviesByCategory(category: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: Long): MovieEntity?

    @Query("SELECT * FROM movies WHERE isBookmarked = 1")
    fun getBookmarkedMovies(): Flow<List<MovieEntity>>

    @Query("UPDATE movies SET isBookmarked = :bookmarked WHERE id = :movieId")
    suspend fun updateBookmark(movieId: Long, bookmarked: Boolean)
}
