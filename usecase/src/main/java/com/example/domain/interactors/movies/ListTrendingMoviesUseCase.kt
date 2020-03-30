package com.example.domain.interactors.movies

import com.example.domain.contracts.IUseCase
import com.example.domain.contracts.movies.MovieRepository
import com.example.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

class ListTrendingMoviesUseCase(private val repository: MovieRepository) :
    IUseCase<Flow<List<Movie>>, ListTrendingMoviesUseCase.ListTrendingMovieParams> {

    override suspend fun execute(params: ListTrendingMovieParams) = repository.listTrending()

    class ListTrendingMovieParams {

    }
}

