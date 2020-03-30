package com.example.domain.interactors.movies

import com.example.domain.contracts.IUseCase
import com.example.domain.contracts.movies.MovieRepository
import com.example.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

class FavoriteMovieUseCase(private val repository: MovieRepository) :
    IUseCase<Flow<Unit>, FavoriteMovieUseCase.FavoriteMovieParams> {

    override suspend fun execute(params: FavoriteMovieParams) = repository.favoriteMovie(
        params.movie
    )

    data class FavoriteMovieParams(val movie: Movie)
}