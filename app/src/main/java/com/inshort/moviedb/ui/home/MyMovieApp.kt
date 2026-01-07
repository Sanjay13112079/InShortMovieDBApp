package com.inshort.moviedb.ui.home

import MovieRepositoryImpl
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.inshort.moviedb.data.local.database.AppDatabase
import com.inshort.moviedb.data.remote.api.MovieDBAPIService
import com.inshort.moviedb.data.remote.api.RetrofitClient
import com.inshort.moviedb.ui.common.AppTopBar
import com.inshort.moviedb.ui.common.currentRoute
import com.inshort.moviedb.ui.movieDetails.MovieDetailScreen
import com.inshort.moviedb.ui.saved.SavedScreen
import com.inshort.moviedb.ui.search.SearchScreen


@Composable
fun MyMovieApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val currentRoute = currentRoute(navController)

    val repository = remember {
        MovieRepositoryImpl(
            apiService = RetrofitClient
                .retrofit
                .create(MovieDBAPIService::class.java),
            movieDao = AppDatabase.getInstance(context.applicationContext).movieDao(),
            apiKey = "ef27c77c8cecae954cd2324c2db4794f"
        )
    }


    Scaffold(
        topBar = {
            AppTopBar(
                title =  when {
                    currentRoute == "home" -> "InShortMovieApp"
                    currentRoute == "search" -> "Search Movies"
                    currentRoute == "saved" -> "Saved Movies"
                    currentRoute?.startsWith("detail") == true -> "Movie Details"
                    else -> "InShortMovieApp"
                }
            )
        },

        bottomBar = {
            if (currentRoute?.startsWith("detail") != true) {
                BottomBar(navController)
            }
        }
    ) { paddingValues ->

        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("home") {
                HomeScreen(repository) {
                    navController.navigate("detail/$it")
                }
            }
            composable("search") {
                SearchScreen(repository) {
                    navController.navigate("detail/$it")
                }
            }
            composable("saved") {
                SavedScreen(repository) {
                    navController.navigate("detail/$it")
                }
            }

            composable(
                route = "detail/{movieId}",
                arguments = listOf(
                    navArgument("movieId") { type = NavType.LongType }
                )
            ) { backStackEntry ->

                val movieId = backStackEntry.arguments?.getLong("movieId") ?: return@composable

                MovieDetailScreen(
                    repository = repository,
                    movieId = movieId
                )
            }
        }

    }
}