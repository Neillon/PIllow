package com.example.data_api.repositories

import com.example.data_api.services.MovieApiService
import com.example.domain.repositories.movies.MovieRemoteRepository
import kotlinx.coroutines.flow.flow

class MovieApiRepository(private val service: MovieApiService) : MovieRemoteRepository {
    override suspend fun search(query: String) = flow {emit(service.search(query).results) }
    override suspend fun listTrending() = flow { emit(service.listTrendingMovies().results) }
    override suspend fun getById(id: Int) = flow { emit(service.getById(id).results) }
}