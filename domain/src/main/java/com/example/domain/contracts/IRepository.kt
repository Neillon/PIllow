package com.example.domain.contracts

import com.example.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface IRepository<T> {
    suspend fun getById(id: Long): Flow<T?>
    suspend fun search(query: String): Flow<List<T?>>
}