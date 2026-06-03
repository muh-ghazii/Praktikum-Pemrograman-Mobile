package com.contoh.filmapp.util

object GenreMapper {

    private val genreMap = mapOf(
        28    to "Aksi",
        12    to "Petualangan",
        16    to "Animasi",
        35    to "Komedi",
        80    to "Kriminal",
        99    to "Dokumenter",
        18    to "Drama",
        10751 to "Keluarga",
        14    to "Fantasi",
        36    to "Sejarah",
        27    to "Horor",
        10402 to "Musik",
        9648  to "Misteri",
        10749 to "Romansa",
        878   to "Fiksi Ilmiah",
        10770 to "TV Movie",
        53    to "Thriller",
        10752 to "Perang",
        37    to "Barat"
    )

    fun getGenreName(id: Int): String {
        return genreMap[id] ?: "Lainnya"
    }

    fun getGenreNames(ids: List<Int>): String {
        if (ids.isEmpty()) return "Lainnya"
        return ids.take(2)
            .mapNotNull { genreMap[it] }
            .joinToString(", ")
            .ifEmpty { "Lainnya" }
    }
}