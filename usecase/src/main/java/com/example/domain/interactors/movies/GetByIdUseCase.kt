package com.example.domain.interactors.movies

import com.example.domain.contracts.IUseCase
import com.example.domain.repositories.movies.MovieRemoteRepository
import com.example.domain.entities.Movie
import com.example.domain.repositories.movies.MovieLocalRepository
import kotlinx.coroutines.flow.Flow

class GetByIdUseCase(
    private val remoteRepository: MovieRemoteRepository,
    private val localRepository: MovieLocalRepository
) :
    IUseCase<Flow<Movie>, GetByIdUseCase.GetByIdParams> {

    override suspend fun execute(params: GetByIdParams) = remoteRepository.getById(
        params.id
    )

    data class GetByIdParams(val id: Long)
}