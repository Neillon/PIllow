package com.example.domain.repositories.movies

import com.example.domain.contracts.IRepository
import com.example.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRemoteRepository: IRepository<Movie> {
    suspend fun listTrending(): Flow<List<Movie>>
}