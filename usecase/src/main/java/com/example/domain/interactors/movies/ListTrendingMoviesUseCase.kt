package com.example.domain.interactors.movies

import com.example.domain.contracts.IUseCase
import com.example.domain.contracts.movies.MovieRepository
import com.example.domain.entities.Movie
import com.example.domain.interactors.NoParams
import kotlinx.coroutines.flow.Flow

class ListTrendingMoviesUseCase(private val repository: MovieRepository) :
    IUseCase<Flow<List<Movie>>, NoParams> {

    override suspend fun execute(params: NoParams) = repository.listTrending()
}

