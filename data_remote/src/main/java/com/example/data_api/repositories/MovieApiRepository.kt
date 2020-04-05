package com.example.data_api.repositories

import com.example.data_api.services.MovieApiService
import com.example.domain.repositories.movies.MovieRemoteRepository

class MovieApiRepository(private val service: MovieApiService) : MovieRemoteRepository {
    override suspend fun search(query: String) = service.search(query)

    override suspend fun listTrending() = service.listTrendingMovies()

    override suspend fun getById(id: Int) = service.getById(id)
}