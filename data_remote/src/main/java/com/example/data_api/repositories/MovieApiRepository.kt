package com.example.data_api.repositories

import com.example.data_api.converters.MovieApiConverter
import com.example.data_api.services.MovieApiService
import com.example.domain.repositories.movies.MovieRemoteRepository
import kotlinx.coroutines.flow.flow

class MovieApiRepository(private val service: MovieApiService) : MovieRemoteRepository{

    override suspend fun search(query: String) = flow {
        emit(service.search(query).results.map { movie ->
            MovieApiConverter.toDomain(movie)
        })
    }

    override suspend fun listTrending(page: Int) = flow {
        emit(service.listTrendingMovies(page).results.map { movie ->
            MovieApiConverter.toDomain(movie)
        })
    }

    override suspend fun getById(id: Long) = flow {
        emit(MovieApiConverter.toDomain(service.getById(id).results))
    }
}