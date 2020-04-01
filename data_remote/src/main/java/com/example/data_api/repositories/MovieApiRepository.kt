package com.example.data_api.repositories

import com.example.data_api.services.MovieApiService
import com.example.domain.contracts.movies.MovieRepository
import com.example.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

class MovieApiRepository(private val api: MovieApiService) : MovieRepository {
    override suspend fun search(query: String) = api.search(query)

    override suspend fun listTrending() = api.listTrendingMovies()

    override suspend fun getById(id: Int) = api.getById(id)

    override suspend fun saveFavoriteMovie(movie: Movie): Flow<Unit> {
        throw NotImplementedError("Method not implemented yet")
    }
}