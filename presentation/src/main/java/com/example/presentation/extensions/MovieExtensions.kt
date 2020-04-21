package com.example.presentation.extensions

import com.example.domain.entities.Movie
import com.example.presentation.binding.MovieBinding
import com.example.presentation.converters.MovieBindingConverter

fun List<Movie?>.convertToBinding(): List<MovieBinding> = this.map { movie ->
    movie?.let {
        MovieBindingConverter.toBinding(it)
    }!!
}
