package com.contoh.filmapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.contoh.filmapp.data.Movie
import com.contoh.filmapp.data.MovieData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

class MovieViewModel(private val appName: String) : ViewModel() {
    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    private val _featuredMovies = MutableStateFlow<List<Movie>>(emptyList())
    val featuredMovies: StateFlow<List<Movie>> = _featuredMovies.asStateFlow()
    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.None)
    val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        val movieList = MovieData.movies
        movieList.forEach { movie ->
            Timber.tag(appName).d("Data item masuk ke list → ID: ${movie.id} | Judul: \"${movie.title}\" | Genre: ${movie.genre} | Rating: ${movie.rating}")
        }
        _movies.value = movieList
        _featuredMovies.value = MovieData.featuredMovies
        Timber.tag(appName).d("loadMovies() selesai. Total film: ${movieList.size}")
    }
    fun onDetailClick(movie: Movie) {
        Timber.tag(appName).d("Tombol Detail ditekan → Film: \"${movie.title}\"")
        Timber.tag(appName).d(
            "Data film dipilih → ID: ${movie.id} | Judul: \"${movie.title}\" | " +
                    "Tahun: ${movie.year} | Genre: ${movie.genre} | Rating: ${movie.rating}"
        )
        _navigationEvent.value = NavigationEvent.NavigateToDetail(movie.id)
    }
    fun onImdbClick(movie: Movie) {
        Timber.tag(appName).d("Tombol IMDB (Explicit Intent) ditekan → Film: \"${movie.title}\" | URL: ${movie.imdbUrl}")
        _navigationEvent.value = NavigationEvent.OpenImdb(movie.imdbUrl)
    }
    fun resetNavigationEvent() {
        _navigationEvent.value = NavigationEvent.None
    }
    sealed class NavigationEvent {
        object None : NavigationEvent()
        data class NavigateToDetail(val movieId: Int) : NavigationEvent()
        data class OpenImdb(val url: String) : NavigationEvent()
    }
}