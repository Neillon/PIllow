package com.example.domain.interactors.movies

import com.example.domain.contracts.IUseCase
import com.example.domain.entities.Movie
import com.example.domain.repositories.movies.MovieLocalRepository
import kotlinx.coroutines.flow.Flow

class DeleteMovieUseCase(
    private val localRepository: MovieLocalRepository
): IUseCase<Unit, DeleteMovieUseCase.DeleteMovieParams> {

    override suspend fun execute(params: DeleteMovieParams) = localRepository.delete(params.movie)

    data class DeleteMovieParams(val movie: Movie)
}