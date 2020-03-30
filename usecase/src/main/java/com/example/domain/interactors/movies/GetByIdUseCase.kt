package com.example.domain.interactors.movies

import com.example.domain.contracts.IUseCase
import com.example.domain.contracts.movies.MovieRepository
import com.example.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

class GetByIdUseCase(private val repository: MovieRepository) :
    IUseCase<Flow<Movie>, GetByIdUseCase.GetByIdParams> {

    override suspend fun execute(params: GetByIdParams) = repository.getById(
        params.id,
        params.apiKey
    )

    data class GetByIdParams(val id: Int, val apiKey: String)

}