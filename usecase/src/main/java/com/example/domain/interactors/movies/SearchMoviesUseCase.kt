package com.example.domain.interactors.movies

import com.example.domain.contracts.IUseCase
import com.example.domain.entities.Movie
import com.example.domain.repositories.movies.MovieLocalRepository
import kotlinx.coroutines.flow.Flow

class SearchMoviesUseCase(
    private val localRepository: MovieLocalRepository
) : IUseCase<Flow<List<Movie?>>, SearchMoviesUseCase.SearchMovieParams> {

    override suspend fun execute(params: SearchMovieParams) = localRepository.search(
        "%${params.query}%"
    )

    data class SearchMovieParams(val query: String)
}