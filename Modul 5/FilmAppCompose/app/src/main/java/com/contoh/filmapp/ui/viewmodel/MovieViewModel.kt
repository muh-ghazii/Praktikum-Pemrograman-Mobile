package com.contoh.filmapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contoh.filmapp.data.Movie
import com.contoh.filmapp.data.repository.MovieRepository
import com.contoh.filmapp.util.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieViewModel(
    private val repository: MovieRepository,
    private val appName: String
) : ViewModel() {

    private val _moviesState = MutableStateFlow<ApiResult<List<Movie>>>(ApiResult.Loading)
    val moviesState: StateFlow<ApiResult<List<Movie>>> = _moviesState.asStateFlow()

    private val _featuredMovies = MutableStateFlow<List<Movie>>(emptyList())
    val featuredMovies: StateFlow<List<Movie>> = _featuredMovies.asStateFlow()

    private val _navigationEvent = MutableStateFlow<NavigationEvent>(NavigationEvent.None)
    val navigationEvent: StateFlow<NavigationEvent> = _navigationEvent.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            repository.getNowPlayingMovies().collect { result ->
                _moviesState.value = result
                if (result is ApiResult.Success) {
                    val movies = result.data
                    _featuredMovies.value = movies.take(6)
                    movies.forEach { movie ->
                        Timber.tag(appName).d(
                            "Data item masuk ke list → ID: ${movie.id} | " +
                                    "Judul: \"${movie.title}\" | Genre: ${movie.genre} | Rating: ${movie.rating}"
                        )
                    }
                    Timber.tag(appName).d("Total film dimuat: ${movies.size}")
                }
            }
        }
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
        Timber.tag(appName).d(
            "Tombol TMDB (Explicit Intent) ditekan → Film: \"${movie.title}\" | URL: ${movie.imdbUrl}"
        )
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