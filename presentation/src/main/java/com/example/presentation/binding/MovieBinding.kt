package com.example.presentation.binding

import java.io.Serializable
import java.util.*

data class MovieBinding(
    var id: Long?,
    val video: Boolean,
    val voteCount: Int,
    val voteAverage: Double,
    val title: String,
    val releaseDate: Date?,
    val originalLanguage: String,
    val originalTitle: String,
    val genreIds: List<Int>,
    val backdropPath: String?,
    var homepage: String? = "",
    val adult: Boolean,
    val overview: String,
    val posterPath: String?,
    val popularity: Double,
    val mediaType: String,
    var favorite: Boolean = false,
    var movieId: Long
) : Serializable
