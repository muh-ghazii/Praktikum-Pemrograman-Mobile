package com.contoh.filmapp.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.contoh.filmapp.data.MovieData
import com.contoh.filmapp.navigation.NavRoutes
import com.contoh.filmapp.ui.components.FeaturedMovieItem
import com.contoh.filmapp.ui.components.MovieListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val columnListState = rememberLazyListState()
    val rowListState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Film Indonesia", fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            state = columnListState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                Text(
                    text = "Featured Film",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        start = 16.dp, top = 16.dp,
                        end = 16.dp, bottom = 8.dp
                    )
                )
                LazyRow(
                    state = rowListState,
                    contentPadding = PaddingValues(horizontal = 10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(
                        items = MovieData.featuredMovies,
                        key = { it.id }
                    ) { movie ->
                        FeaturedMovieItem(movie = movie)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            }
            item {
                Text(
                    text = "Semua Film",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        start = 16.dp, top = 12.dp,
                        end = 16.dp, bottom = 4.dp
                    )
                )
            }

            items(
                items = MovieData.movies,
                key = { it.id }
            ) { movie ->
                MovieListItem(
                    movie = movie,
                    onImdbClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(movie.imdbUrl)
                        )
                        context.startActivity(intent)
                    },
                    onDetailClick = {
                        navController.navigate(
                            NavRoutes.Detail.createRoute(movie.id)
                        )
                    }
                )
            }
        }
    }
}