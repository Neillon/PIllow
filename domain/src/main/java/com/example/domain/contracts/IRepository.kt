package com.example.domain.contracts

import kotlinx.coroutines.flow.Flow

interface IRepository<T> {
    suspend fun getById(id: Int): Flow<T>
}