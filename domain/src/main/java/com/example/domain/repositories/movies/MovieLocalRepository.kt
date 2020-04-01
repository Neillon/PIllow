package com.example.domain.repositories.movies

import com.example.domain.contracts.IRepository
import com.example.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalRepository: IRepository<Movie>{
    suspend fun saveFavoriteMovie(movie: Movie): Flow<Unit>
}