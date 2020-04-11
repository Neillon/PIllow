package com.example.domain.interactors.movies

import com.example.domain.contracts.IUseCase
import com.example.domain.entities.Movie
import com.example.domain.repositories.movies.MovieLocalRepository
import kotlinx.coroutines.flow.Flow

class DeleteMovieUseCase(
    private val localRepository: MovieLocalRepository
): IUseCase<Flow<Unit>, DeleteMovieUseCase.Params> {

    override suspend fun execute(params: Params) = localRepository.delete(params.id)

    data class Params(val id: Long)
}