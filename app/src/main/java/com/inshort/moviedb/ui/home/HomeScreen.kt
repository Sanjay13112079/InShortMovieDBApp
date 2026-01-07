package com.inshort.moviedb.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.inshort.moviedb.data.repository.MovieRepository
import com.inshort.moviedb.ui.common.BookmarkStar
import com.inshort.moviedb.ui.common.PosterImage


@Composable
fun HomeScreen(
    repository: MovieRepository,
    onMovieClick: (Long) -> Unit
) {
    val homeViewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(repository)
    )
    val homeUiState by homeViewModel.uiState.collectAsState()

    if (homeUiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Trending",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        LazyRow(
            modifier = Modifier.padding(vertical = 8.dp),
            state = rememberLazyListState()
        ) {
            items(homeUiState.trendingMovies) { movie ->
                Box(
                    modifier = Modifier
                        .width(140.dp)
                        .height(210.dp)
                        .padding(end = 8.dp)
                        .clickable { onMovieClick(movie.id) }
                        .background(Color.LightGray)
                ) {

                    PosterImage(
                        posterPath = movie.poster_path,
                        modifier = Modifier.fillMaxSize()
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.7f)
                                    )
                                )
                            )
                    )

                    BookmarkStar(
                        isBookmarked = movie.isBookMarked,
                        onClick = { homeViewModel.onBookMarkClicked(movie) },
                        modifier = Modifier.align(Alignment.TopEnd)
                    )

                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Text(
            text = "Now Playing",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        LazyColumn(
            modifier = Modifier
                .padding(vertical = 8.dp),
            state = rememberLazyListState()
        ) {
            items(homeUiState.nowPlayingMovies) { movie ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(Color.LightGray)
                        .clickable { onMovieClick(movie.id) }
                ) {
                    PosterImage(
                        posterPath = movie.poster_path,
                        modifier = Modifier
                            .size(width = 130.dp, height = 150.dp)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(
                        modifier = Modifier.weight(1f)
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = movie.title?:"",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 2
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Release • ${movie.release_date}",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "⭐ ${movie.vote_average}",
                            fontSize = 13.sp,
                            color = Color.Black
                        )
                    }

                    BookmarkStar(
                        movie.isBookMarked,
                        onClick = { homeViewModel.onBookMarkClicked(movie) })
                }

            }

        }
    }

}


//@Preview(showBackground = true)
//@Composable
//fun previewApp() {
//    HomeScreen()
//}