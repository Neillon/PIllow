package com.example.domain.interactors.movies

import com.example.domain.contracts.IUseCase
import com.example.domain.entities.Movie
import com.example.domain.interactors.NoParams
import com.example.domain.repositories.movies.MovieLocalRepository
import kotlinx.coroutines.flow.Flow

class ListFavoriteMoviesUseCase(
    private val localRepository: MovieLocalRepository
): IUseCase<Flow<List<Movie>>, NoParams> {
    override suspend fun execute(params: NoParams) = localRepository.listFavoriteMovies()
}