package com.example.data_local.extesions

import com.example.data_local.converters.MovieConverter
import com.example.data_local.entity.MovieEntity

fun List<MovieEntity>.convertToDomain() = this.map { movieEntity -> MovieConverter.toDomain(movieEntity) }

fun MovieEntity.convertToDomain() = MovieConverter.toDomain(this)