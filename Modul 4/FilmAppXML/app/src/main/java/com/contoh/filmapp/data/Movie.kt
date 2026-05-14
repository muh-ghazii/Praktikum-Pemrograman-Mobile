package com.contoh.filmapp.data

data class Movie (
    val id: Int,
    val title: String,
    val year: Int,
    val genre: String,
    val rating: Float,
    val plot: String,
    val imageUrl: String,
    val imdbUrl: String
)