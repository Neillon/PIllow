package com.example.domain.interactors.movies

import com.example.domain.contracts.IUseCase
import com.example.domain.contracts.movies.MovieRepository
import com.example.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

class ListAllMoviesUseCase(private val repository: MovieRepository) :
    IUseCase<Flow<List<Movie>>, ListAllMoviesUseCase.ListMovieParams> {

    override suspend fun execute(params: ListMovieParams) = repository.listAll(
        params.page,
        params.apiKey
    )

    data class ListMovieParams(val page: Int = 1, val apiKey: String = "")
}