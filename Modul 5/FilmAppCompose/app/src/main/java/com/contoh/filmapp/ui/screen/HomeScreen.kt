package com.contoh.filmapp.ui.screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.contoh.filmapp.FilmApp
import com.contoh.filmapp.navigation.NavRoutes
import com.contoh.filmapp.ui.components.FeaturedMovieItem
import com.contoh.filmapp.ui.components.MovieListItem
import com.contoh.filmapp.ui.viewmodel.MovieViewModel
import com.contoh.filmapp.ui.viewmodel.MovieViewModelFactory
import com.contoh.filmapp.util.ApiResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val app = context.applicationContext as FilmApp

    val viewModel: MovieViewModel = viewModel(
        factory = MovieViewModelFactory(app.movieRepository, "FilmApp")
    )

    val moviesState     by viewModel.moviesState.collectAsStateWithLifecycle()
    val featuredMovies  by viewModel.featuredMovies.collectAsStateWithLifecycle()
    val navigationEvent by viewModel.navigationEvent.collectAsStateWithLifecycle()
    val columnListState = rememberLazyListState()
    val rowListState    = rememberLazyListState()

    LaunchedEffect(navigationEvent) {
        when (val event = navigationEvent) {
            is MovieViewModel.NavigationEvent.NavigateToDetail -> {
                navController.navigate(NavRoutes.Detail.createRoute(event.movieId))
                viewModel.resetNavigationEvent()
            }
            is MovieViewModel.NavigationEvent.OpenImdb -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.url))
                context.startActivity(intent)
                viewModel.resetNavigationEvent()
            }
            is MovieViewModel.NavigationEvent.None -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Film Indonesia", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                )
            )
        }
    ) { innerPadding ->
        when (val state = moviesState) {
            is ApiResult.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Memuat film...",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            is ApiResult.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Text(
                            text = "😕",
                            fontSize = 48.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Gagal memuat film",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.message,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(onClick = { viewModel.loadMovies() }) {
                            Text("Coba Lagi")
                        }
                    }
                }
            }

            is ApiResult.Success -> {
                val movies = state.data

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
                            items(items = featuredMovies, key = { it.id }) { movie ->
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

                    items(items = movies, key = { it.id }) { movie ->
                        MovieListItem(
                            movie         = movie,
                            onImdbClick   = { viewModel.onImdbClick(movie) },
                            onDetailClick = { viewModel.onDetailClick(movie) }
                        )
                    }
                }
            }
        }
    }
}