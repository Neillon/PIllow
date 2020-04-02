package com.example.data_local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.data_local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = REPLACE)
    suspend fun create(vararg movieEntity: MovieEntity): Flow<Unit>

    @Query("SELECT * FROM MovieEntity WHERE upper(movie_name) LIKE upper(:name)")
    suspend fun search(name: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity")
    suspend fun listAll(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity WHERE movie_id = :id")
    suspend fun getById(id: Int): Flow<MovieEntity>
}