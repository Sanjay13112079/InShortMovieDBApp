package com.inshort.moviedb.ui.movieDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.inshort.moviedb.data.repository.MovieRepository
import com.inshort.moviedb.ui.common.BookmarkStar
import com.inshort.moviedb.ui.common.PosterImage


@Composable
fun MovieDetailScreen(
    repository: MovieRepository,
    movieId: Long
) {
    val viewModel: MovieDetailsViewModel = viewModel(
        factory = MovieDetailsViewModelFactory(repository, movieId)
    )

    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.errorMessage != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(uiState.errorMessage!!)
            }
        }

        uiState.movieDetail != null -> {
            val movie = uiState.movieDetail!!

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f / 3f)// poster height
                ) {
                    PosterImage(
                        posterPath = movie.poster_path,
                        modifier = Modifier
                            .fillMaxSize()
                    )

                    BookmarkStar(
                        isBookmarked = movie.isBookMarked,
                        modifier = Modifier.align(Alignment.TopEnd),
                        onClick = { viewModel.onBookMarkClicked(movie) }
                    )

                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = movie.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Genres: ${
                        movie.genres.joinToString { it.name }
                    }"
                )
                Spacer(modifier = Modifier.height(8.dp))

                if (!movie.tagline.isNullOrBlank()) {
                    Text(text = movie.tagline)
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Text(text = "Release: ${movie.release_date}")

                Text(text = "Rating: ${movie.vote_average}")

                Text(text = "Runtime: ${movie.runtime ?: "N/A"} min")

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = movie.overview)

                Spacer(modifier = Modifier.height(12.dp))

            }


        }
    }
}
