package com.example.domain.interactors.movies

import com.example.domain.contracts.IUseCase
import com.example.domain.repositories.movies.MovieRemoteRepository
import com.example.domain.entities.Movie
import com.example.domain.repositories.movies.MovieLocalRepository
import kotlinx.coroutines.flow.Flow

class SaveFavoriteMovieUseCase(
    private val localRepository: MovieLocalRepository
) : IUseCase<Flow<Unit>, SaveFavoriteMovieUseCase.FavoriteMovieParams> {

    override suspend fun execute(params: FavoriteMovieParams) = localRepository.saveFavoriteMovie(
        params.movie
    )

    data class FavoriteMovieParams(val movie: Movie)
}