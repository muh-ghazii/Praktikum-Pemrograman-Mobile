package com.contoh.filmapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.contoh.filmapp.data.local.dao.MovieDao
import com.contoh.filmapp.data.local.entity.MovieEntity

/**
 * Room Database untuk aplikasi FilmApp.
 *
 * @Database mendefinisikan:
 * - entities  : daftar tabel yang ada di database
 * - version   : versi database, increment jika ada perubahan schema
 * - exportSchema: false agar tidak membuat file schema (tidak diperlukan di praktikum)
 */
@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        /**
         * Singleton pattern untuk memastikan hanya ada satu instance database.
         * @Volatile memastikan nilai INSTANCE selalu terbaru di semua thread.
         * synchronized memastikan tidak ada dua thread yang membuat instance secara bersamaan.
         */
        fun getInstance(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "film_app_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}