package com.contoh.filmapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.contoh.filmapp.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies ORDER BY vote_average DESC")
    fun getAllMovies(): Flow<List<MovieEntity>>
    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId: Int): Flow<MovieEntity?>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)
    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()
    @Query("SELECT cached_at FROM movies ORDER BY cached_at DESC LIMIT 1")
    suspend fun getLastCacheTime(): Long?

    @Query("SELECT COUNT(*) FROM movies")
    suspend fun getMovieCount(): Int
}