package com.example.data_local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Long? = null,
    @ColumnInfo(name = "movie_id") val movieId: Long,
    @ColumnInfo(name = "movie_name") val movieName: String,
    @ColumnInfo(name = "movie_poster") val moviePoster: String?
)