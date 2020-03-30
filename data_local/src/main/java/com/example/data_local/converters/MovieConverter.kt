package com.example.data_local.converters

import com.example.data_local.entity.MovieEntity
import com.example.domain.entities.Movie
import java.util.*

object MovieConverter {

    fun toDomain(movieEntity: MovieEntity) = Movie(
        id = movieEntity.movieId,
        title = movieEntity.movieName,
        overview = "",
        popularity = 0.0,
        homepage = "",
        voteAverage = 0.0,
        releaseDate = Date(),
        posterPath = movieEntity.moviePoster,
        backdropPath = movieEntity.moviePoster
    )

    fun toEntity(movie: Movie) = MovieEntity(
        id = 0,
        movieId = movie.id,
        movieName = movie.title,
        moviePoster = movie.posterPath
    )
}