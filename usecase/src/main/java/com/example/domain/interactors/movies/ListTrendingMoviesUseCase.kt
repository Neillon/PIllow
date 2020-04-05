package com.example.domain.interactors.movies

import com.example.domain.contracts.IUseCase
import com.example.domain.repositories.movies.MovieRemoteRepository
import com.example.domain.entities.Movie
import com.example.domain.interactors.NoParams
import com.example.domain.repositories.movies.MovieLocalRepository
import kotlinx.coroutines.flow.Flow

class ListTrendingMoviesUseCase(
    private val remoteRepository: MovieRemoteRepository
) : IUseCase<Flow<List<Movie>>, NoParams> {
    override suspend fun execute(params: NoParams) = remoteRepository.listTrending()
}

