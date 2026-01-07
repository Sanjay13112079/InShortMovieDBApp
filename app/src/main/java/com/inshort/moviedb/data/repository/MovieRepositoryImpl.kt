import com.inshort.moviedb.data.local.dao.MovieDao
import com.inshort.moviedb.data.local.entity.MovieCategoryEntity
import com.inshort.moviedb.data.mappers.toEntity
import com.inshort.moviedb.data.mappers.toMovie
import com.inshort.moviedb.data.remote.api.MovieDBAPIService
import com.inshort.moviedb.data.remote.datasource.NetworkDataSource
import com.inshort.moviedb.data.remote.datasource.NetworkResult
import com.inshort.moviedb.data.remote.model.Movie
import com.inshort.moviedb.data.remote.model.MovieDetailResponse
import com.inshort.moviedb.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val apiService: MovieDBAPIService,
    private val movieDao: MovieDao,
    private val apiKey: String
) : MovieRepository {

    override suspend fun observeTrendingMovies(): Flow<List<Movie>> {
        return movieDao
            .getMoviesByCategory("TRENDING")
            .map { list ->
                list.map { it.toMovie() }
            }
    }

    override suspend fun observeNowPlayingMovies(): Flow<List<Movie>> {
        return movieDao
            .getMoviesByCategory("NOW_PLAYING")
            .map { list ->
                list.map { it.toMovie() }
            }
    }

    override suspend fun fetchTrendingMoviesAndSaveToDB() {
        val result = NetworkDataSource.execute {
            apiService.getTrendingMovies(apiKey)
        }

        if (result is NetworkResult.Success) {
            movieDao.insertMovies(
                result.data.results.map {
                    it.toEntity()
                }
            )
            movieDao.insertMovieCategory(
                result.data.results.map {
                    MovieCategoryEntity(it.id, "TRENDING")
                }
            )

        }
    }

    override suspend fun fetchNowPlayingMoviesAndSaveToDB() {
        val result = NetworkDataSource.execute {
            apiService.getNowPlayingMovies(apiKey)
        }

        if (result is NetworkResult.Success) {
            movieDao.insertMovies(
                result.data.results.map {
                    it.toEntity()
                }
            )
            movieDao.insertMovieCategory(
                result.data.results.map {
                    MovieCategoryEntity(it.id, "NOW_PLAYING")
                }
            )
        }
    }


    override suspend fun getBookmarkedMovies(): Flow<List<Movie>> {
        return movieDao
            .getBookmarkedMovies()
            .map { list ->
                list.map { it.toMovie() }
            }
    }

    override suspend fun updateBookmark(movieId: Long, bookmarked: Boolean) {
        movieDao.updateBookmark(movieId, bookmarked)
    }

    override suspend fun updateBookmarkFromSearch(
        movie: Movie,
        bookmarked: Boolean
    ) {

        val entity = movie.toEntity(
            bookMarked = bookmarked
        )
        movieDao.insertMovies(listOf(entity))

        if (bookmarked) {
            movieDao.insertMovieCategory(
                listOf(MovieCategoryEntity(movie.id, "SEARCH"))
            )
        }
    }

    override suspend fun searchMovies(query: String): List<Movie> {

        val result = NetworkDataSource.execute {
            apiService.searchMovies(
                apiKey = apiKey,
                query = query
            )
        }

        return when (result) {
            is NetworkResult.Success -> result.data.results
            is NetworkResult.Error -> emptyList()
        }
    }

    override suspend fun getMovieDetails(movieId: Long): MovieDetailResponse {

        val result = NetworkDataSource.execute {
            apiService.getMovieDetails(
                apiKey = apiKey,
                movieId = movieId
            )
        }
        return when (result) {
            is NetworkResult.Success -> result.data
            is NetworkResult.Error -> {
                throw Exception(result.message)
            }
        }

    }

    override suspend fun getMovieById(movieId: Long): Movie? {
        return movieDao.getMovieById(movieId)?.toMovie()
    }
}
