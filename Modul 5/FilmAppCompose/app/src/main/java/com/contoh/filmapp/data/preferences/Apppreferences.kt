package com.contoh.filmapp.data.preferences

import android.content.Context
import androidx.core.content.edit

class AppPreferences(context: Context) {

    private val prefs = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    var language: String
        get() = prefs.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE) ?: DEFAULT_LANGUAGE
        set(value) = prefs.edit { putString(KEY_LANGUAGE, value) }

    var lastViewedMovieId: Int
        get() = prefs.getInt(KEY_LAST_VIEWED, -1)
        set(value) = prefs.edit { putInt(KEY_LAST_VIEWED, value) }

    var isFirstLaunch: Boolean
        get() = prefs.getBoolean(KEY_FIRST_LAUNCH, true)
        set(value) = prefs.edit { putBoolean(KEY_FIRST_LAUNCH, value) }

    var lastRefreshTime: Long
        get() = prefs.getLong(KEY_LAST_REFRESH, 0L)
        set(value) = prefs.edit { putLong(KEY_LAST_REFRESH, value) }

    companion object {
        private const val PREFS_NAME       = "film_app_preferences"
        private const val KEY_LANGUAGE     = "key_language"
        private const val KEY_LAST_VIEWED  = "key_last_viewed_movie_id"
        private const val KEY_FIRST_LAUNCH = "key_is_first_launch"
        private const val KEY_LAST_REFRESH = "key_last_refresh_time"
        private const val DEFAULT_LANGUAGE = "id-ID"
    }
}