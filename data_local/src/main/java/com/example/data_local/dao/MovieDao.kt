package com.example.data_local.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import com.example.data_local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(movieEntity: MovieEntity): Long

    @Query("SELECT * FROM movie WHERE upper(movie_name) LIKE upper(:name)")
    fun search(name: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie")
    fun listAll(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getById(id: Long): Flow<MovieEntity?>

    @Delete
    suspend fun delete(movie: MovieEntity)
}