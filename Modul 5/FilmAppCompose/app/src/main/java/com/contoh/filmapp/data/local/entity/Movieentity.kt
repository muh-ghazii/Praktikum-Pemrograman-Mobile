package com.contoh.filmapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.contoh.filmapp.data.Movie
import com.contoh.filmapp.util.GenreMapper

/**
 * Entity Room untuk menyimpan data film ke database lokal.
 * Setiap properti dipetakan ke kolom di tabel "movies".
 *
 * @cachedAt menyimpan timestamp saat data di-cache, digunakan
 * untuk menentukan apakah cache masih fresh atau sudah kadaluarsa.
 */
@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")           val id: Int,
    @ColumnInfo(name = "title")        val title: String,
    @ColumnInfo(name = "overview")     val overview: String,
    @ColumnInfo(name = "poster_path")  val posterPath: String?,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "genre_ids")    val genreIds: String,
    @ColumnInfo(name = "cached_at")    val cachedAt: Long = System.currentTimeMillis()
) {
    fun toDomain(): Movie {
        val genreIdList = genreIds
            .split(",")
            .mapNotNull { it.trim().toIntOrNull() }

        val year = releaseDate
            .takeIf { it.length >= 4 }
            ?.substring(0, 4)
            ?.toIntOrNull() ?: 0

        val imageUrl = if (!posterPath.isNullOrEmpty()) {
            "https://image.tmdb.org/t/p/w500$posterPath"
        } else {
            ""
        }

        return Movie(
            id       = id,
            title    = title,
            year     = year,
            genre    = GenreMapper.getGenreNames(genreIdList),
            rating   = voteAverage.toFloat(),
            plot     = overview,
            imageUrl = imageUrl,
            imdbUrl  = "https://www.themoviedb.org/movie/$id"
        )
    }
}