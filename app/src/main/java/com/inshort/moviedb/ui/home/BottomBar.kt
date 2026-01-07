package com.inshort.moviedb.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.inshort.moviedb.ui.common.currentRoute


@Composable
fun BottomBar(navController: NavHostController) {

    val currentRoute = currentRoute(navController)

    Column(modifier = Modifier.padding(bottom = 8.dp)
    ) {
        HorizontalDivider(thickness = 1.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            BottomBarItem(
                title = "Home",
                selected = currentRoute == "home",
                onClick = {
                    navController.navigate("home") {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )


            BottomBarItem(
                title = "Search",
                selected = currentRoute == "search",
                onClick = {
                    navController.navigate("search") {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            BottomBarItem(
                title = "Saved",
                selected = currentRoute == "saved",
                onClick = {
                    navController.navigate("saved") {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}





@Composable
fun BottomBarItem(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Top indicator
        if (selected) {
            Box(
                modifier = Modifier
                    .height(3.dp)
                    .width(32.dp)
                    .background(Color.Blue)
            )
        } else {
            Spacer(modifier = Modifier.height(3.dp))
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = title,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) Color.Black else Color.Gray
        )
    }
}
