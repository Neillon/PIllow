package com.example.data_api.converters

import com.example.data_api.schemas.MovieSchema
import com.example.domain.entities.Movie

object MovieApiConverter {
    fun toSchema(movie: Movie) = MovieSchema(
        id = movie.id!!,
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

    fun toDomain(movieSchema: MovieSchema) = Movie(
        title = movieSchema.title,
        overview = movieSchema.overview,
        popularity = movieSchema.popularity,
        homepage = movieSchema.homepage,
        voteAverage = movieSchema.voteAverage,
        releaseDate = movieSchema.releaseDate,
        posterPath = movieSchema.posterPath,
        backdropPath = movieSchema.backdropPath,
        movieId = movieSchema.id
    )
}