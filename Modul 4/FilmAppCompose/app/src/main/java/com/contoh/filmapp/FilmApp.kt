package com.contoh.filmapp

import android.app.Application
import timber.log.Timber

class FilmApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.d("FilmApp: Application onCreate() dipanggil")
        }
    }
}