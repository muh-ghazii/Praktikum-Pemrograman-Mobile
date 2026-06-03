package com.contoh.filmapp.data.repository

import com.contoh.filmapp.data.Movie
import com.contoh.filmapp.data.local.dao.MovieDao
import com.contoh.filmapp.data.local.entity.MovieEntity
import com.contoh.filmapp.data.preferences.AppPreferences
import com.contoh.filmapp.data.remote.api.TmdbApiService
import com.contoh.filmapp.util.ApiResult
import com.contoh.filmapp.util.GenreMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class MovieRepository(
    private val apiService: TmdbApiService,
    private val movieDao: MovieDao,
    private val appPreferences: AppPreferences
) {

    companion object {
        private const val CACHE_EXPIRY_MS = 60 * 60 * 1000L
        private const val TAG = "MovieRepository"
    }

    fun getNowPlayingMovies(): Flow<ApiResult<List<Movie>>> = flow {
        Timber.tag(TAG).d("getNowPlayingMovies() dipanggil")
        emit(ApiResult.Loading)

        val cachedMovies = movieDao.getAllMovies().first()
        Timber.tag(TAG).d("Cache: ${cachedMovies.size} film ditemukan")

        if (cachedMovies.isNotEmpty()) {
            emit(ApiResult.Success(cachedMovies.map { it.toDomain() }))
            Timber.tag(TAG).d("Cache data di-emit ke UI")
        }

        val lastCacheTime = movieDao.getLastCacheTime()
        val isCacheStale = lastCacheTime == null ||
                System.currentTimeMillis() - lastCacheTime > CACHE_EXPIRY_MS

        Timber.tag(TAG).d("Cache stale: $isCacheStale")

        if (isCacheStale) {
            try {
                Timber.tag(TAG).d("Fetching dari TMDB API...")
                val response = apiService.getNowPlayingMovies(
                    language = appPreferences.language
                )
                val entities = response.results.map { dto ->
                    MovieEntity(
                        id          = dto.id,
                        title       = dto.title,
                        overview    = dto.overview,
                        posterPath  = dto.posterPath,
                        voteAverage = dto.voteAverage,
                        releaseDate = dto.releaseDate,
                        genreIds    = dto.genreIds.joinToString(","),
                        cachedAt    = System.currentTimeMillis()
                    )
                }
                movieDao.deleteAllMovies()
                movieDao.insertMovies(entities)
                appPreferences.lastRefreshTime = System.currentTimeMillis()

                Timber.tag(TAG).d("${entities.size} film berhasil disimpan ke Room")

                val freshMovies = movieDao.getAllMovies().first()
                emit(ApiResult.Success(freshMovies.map { it.toDomain() }))

            } catch (e: HttpException) {
                val errorMsg = "HTTP Error ${e.code()}: ${e.message()}"
                Timber.tag(TAG).e(errorMsg)
                if (cachedMovies.isEmpty()) {
                    emit(ApiResult.Error(errorMsg))
                }

            } catch (e: IOException) {
                val errorMsg = "Tidak ada koneksi internet"
                Timber.tag(TAG).e(errorMsg)
                if (cachedMovies.isEmpty()) {
                    emit(ApiResult.Error(errorMsg))
                }

            } catch (e: Exception) {
                val errorMsg = "Terjadi kesalahan: ${e.message}"
                Timber.tag(TAG).e(errorMsg)
                if (cachedMovies.isEmpty()) {
                    emit(ApiResult.Error(errorMsg))
                }
            }
        }
    }.flowOn(Dispatchers.IO)
}