package com.example.domain.entities

import java.util.Date

data class Movie(
    var id: Int,
    var title: String = "",
    var overview: String = "",
    var popularity: Double,
    var homepage: String = "",
    var voteAverage: Double,
    var releaseDate: Date,
    var posterPath: String = "",
    var backdropPath: String = ""
)