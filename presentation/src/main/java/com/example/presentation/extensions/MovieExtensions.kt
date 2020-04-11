package com.example.presentation.extensions

import com.example.domain.entities.Movie
import com.example.presentation.converters.MovieBindingConverter

fun List<Movie>.convertToBinding() = this.map { movie -> MovieBindingConverter.toBinding(movie) }