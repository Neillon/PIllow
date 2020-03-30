package com.example.domain.contracts.movies

import com.example.domain.contracts.IRepository
import com.example.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository: IRepository<Movie> {
    suspend fun search(query: String): Flow<List<Movie>>
    suspend fun saveFavoriteMovie(movie: Movie): Flow<Unit>
}