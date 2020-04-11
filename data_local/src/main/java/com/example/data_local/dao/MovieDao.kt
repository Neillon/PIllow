package com.example.data_local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.data_local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = REPLACE)
    fun create(movieEntity: MovieEntity): Long

    @Query("SELECT * FROM movie WHERE upper(movie_name) LIKE upper(:name)")
    fun search(name: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie")
    fun listAll(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM movie WHERE movie_id = :id")
    fun getById(id: Long): Flow<MovieEntity>

    @Query("DELETE FROM movie WHERE id = :id")
    fun delete(id: Long)
}