package com.example.data_local.converters

import com.example.data_local.entity.MovieEntity
import com.example.domain.entities.Movie
import java.util.*

object MovieConverter {

    fun toDomain(movieEntity: MovieEntity) = Movie(
        id = movieEntity.id,
        title = movieEntity.movieName,
        overview = movieEntity.movieOverview ?: "",
        popularity = 0.0,
        homepage = "",
        voteAverage = movieEntity.voteAverage,
        releaseDate = Date(),
        posterPath = movieEntity.moviePoster,
        backdropPath = movieEntity.moviePoster,
        movieId = movieEntity.movieId
    )

    fun toEntity(movie: Movie) = MovieEntity(
        id = movie.id,
        movieId = movie.movieId,
        movieName = movie.title,
        movieOverview = movie.overview,
        moviePoster = movie.posterPath,
        voteAverage = movie.voteAverage
    )
}