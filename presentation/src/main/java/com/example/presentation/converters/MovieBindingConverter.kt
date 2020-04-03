package com.example.presentation.converters

import com.example.domain.entities.Movie
import com.example.presentation.binding.MovieBinding

object MovieBindingConverter {

    fun toBinding(movie: Movie) = MovieBinding(
        id = movie.id,
        title = movie.title,
        overview = movie.overview,
        popularity = movie.popularity,
        homepage = movie.homepage,
        voteAverage = movie.voteAverage,
        releaseDate = movie.releaseDate,
        posterPath = movie.posterPath,
        backdropPath = movie.backdropPath,
        adult = false,
        genreIds = listOf(),
        mediaType = "movie",
        originalLanguage = "en",
        originalTitle = movie.title,
        video = false,
        voteCount = 0
    )

    fun toDomain(movieSchema: MovieBinding) = Movie(
        id = movieSchema.id,
        title = movieSchema.title,
        overview = movieSchema.overview,
        popularity = movieSchema.popularity,
        homepage = movieSchema.homepage,
        voteAverage = movieSchema.voteAverage,
        releaseDate = movieSchema.releaseDate,
        posterPath = movieSchema.posterPath,
        backdropPath = movieSchema.backdropPath
    )
}