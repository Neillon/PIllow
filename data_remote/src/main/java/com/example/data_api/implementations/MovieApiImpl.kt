package com.example.data_api.implementations

import com.example.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface MovieApiImpl {

    @GET()
    suspend fun listTrendingMovies(): Flow<List<Movie>>

    @GET
    suspend fun getById(id: Int): Flow<Movie>

    @GET
    suspend fun search(query: String): Flow<List<Movie>>

    @GET
    suspend fun saveFavoriteMovie(movie: Movie): Flow<Unit>
}