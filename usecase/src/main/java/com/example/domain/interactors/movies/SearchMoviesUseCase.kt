package com.example.domain.interactors.movies

import com.example.domain.contracts.IUseCase
import com.example.domain.contracts.movies.MovieRepository
import com.example.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

class SearchMoviesUseCase(private val repository: MovieRepository) :
    IUseCase<Flow<List<Movie>>, SearchMoviesUseCase.SearchMovieParams> {

    override suspend fun execute(params: SearchMovieParams) = repository.search(
        params.query,
        params.page,
        params.apiKey
    )

    data class SearchMovieParams(val query: String, val page: Int = 1, val apiKey: String = "")
}