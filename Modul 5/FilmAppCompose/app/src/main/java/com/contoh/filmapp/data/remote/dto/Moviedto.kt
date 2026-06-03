package com.contoh.filmapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object untuk satu item film dari TMDB API.
 * Menggunakan @Serializable dari KotlinX Serialization.
 * @SerialName memetakan nama field JSON ke nama properti Kotlin.
 */
@Serializable
data class MovieDto(
    @SerialName("id")           val id: Int,
    @SerialName("title")        val title: String,
    @SerialName("overview")     val overview: String,
    @SerialName("poster_path")  val posterPath: String? = null,
    @SerialName("vote_average") val voteAverage: Double = 0.0,
    @SerialName("release_date") val releaseDate: String = "",
    @SerialName("genre_ids")    val genreIds: List<Int> = emptyList()
)