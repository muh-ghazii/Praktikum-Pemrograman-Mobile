package com.contoh.filmapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("page")          val page: Int = 1,
    @SerialName("results")       val results: List<MovieDto> = emptyList(),
    @SerialName("total_pages")   val totalPages: Int = 0,
    @SerialName("total_results") val totalResults: Int = 0
)