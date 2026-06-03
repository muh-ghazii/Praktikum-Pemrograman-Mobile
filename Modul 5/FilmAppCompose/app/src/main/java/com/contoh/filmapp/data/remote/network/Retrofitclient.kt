package com.contoh.filmapp.data.remote.network

import com.contoh.filmapp.data.remote.api.TmdbApiService
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object RetrofitClient {
    const val TMDB_API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxMzIwNTBlZDlkMjIzNzVjYWZhZjZjODU4YWMwZTAwOSIsIm5iZiI6MTc4MDIwMjUzMy4wOTEsInN1YiI6IjZhMWJiYzI1N2ZkMTQ1OTQ2NTUyYmUwZCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.E40Zevc9oMpFv1Es_CyHH05HwDduYjYIRdJUAXggmgQ"
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $TMDB_API_KEY")
                .addHeader("accept", "application/json")
                .build()
            chain.proceed(request)
        }
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    val apiService: TmdbApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(
                json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(TmdbApiService::class.java)
    }
}