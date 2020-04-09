package com.example.data_local.repositories

import com.example.data_local.converters.MovieConverter
import com.example.data_local.database.AppDatabase
import com.example.domain.repositories.movies.MovieLocalRepository
import com.example.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class MovieLocalRepository(database: AppDatabase) : MovieLocalRepository {
    private val dao = database.movieDao()

    override suspend fun getById(id: Long): Flow<Movie> =
        dao.getById(id)
            .map { movieEntity ->
                MovieConverter.toDomain(movieEntity)
            }

    override suspend fun search(query: String) =
        dao.search(query)
            .map { movies ->
                movies.map { movieEntity ->
                    MovieConverter.toDomain(movieEntity)
                }
            }

    override suspend fun saveFavoriteMovie(movie: Movie): Flow<Movie> {
        var id = dao.create(MovieConverter.toEntity(movie))

        return getById(id)
    }
}