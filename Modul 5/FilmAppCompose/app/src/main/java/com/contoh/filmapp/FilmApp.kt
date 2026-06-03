package com.contoh.filmapp

import android.app.Application
import com.contoh.filmapp.data.local.database.MovieDatabase
import com.contoh.filmapp.data.preferences.AppPreferences
import com.contoh.filmapp.data.remote.network.RetrofitClient
import com.contoh.filmapp.data.repository.MovieRepository
import timber.log.Timber

class FilmApp : Application() {
    val database: MovieDatabase by lazy {
        MovieDatabase.getInstance(this)
    }
    val appPreferences: AppPreferences by lazy {
        AppPreferences(this)
    }
    val movieRepository: MovieRepository by lazy {
        MovieRepository(
            apiService     = RetrofitClient.apiService,
            movieDao       = database.movieDao(),
            appPreferences = appPreferences
        )
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("FilmApp: Application onCreate() dipanggil")
        }
    }
}