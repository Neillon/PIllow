package com.example.data_api.repositories

import com.example.data_api.services.MovieApiService
import com.example.domain.repositories.movies.MovieRemoteRepository
import com.example.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

class MovieApiRepository(private val api: MovieApiService) : MovieRemoteRepository {
    override suspend fun search(query: String) = api.search(query)

    override suspend fun listTrending() = api.listTrendingMovies()

    override suspend fun getById(id: Int) = api.getById(id)
}