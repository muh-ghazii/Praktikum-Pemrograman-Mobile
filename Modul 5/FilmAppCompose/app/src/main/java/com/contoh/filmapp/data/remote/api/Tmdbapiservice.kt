package com.contoh.filmapp.data.remote.api

import com.contoh.filmapp.data.remote.dto.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApiService {

    /**
     * Mengambil daftar film yang sedang tayang di bioskop.
     * Endpoint: GET /movie/now_playing
     *
     * @param language Bahasa response, default "id-ID" (Indonesia)
     * @param page     Halaman data yang diminta, default 1
     */
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "id-ID",
        @Query("page")     page: Int = 1
    ): MovieResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "id-ID",
        @Query("page")     page: Int = 1
    ): MovieResponse
}