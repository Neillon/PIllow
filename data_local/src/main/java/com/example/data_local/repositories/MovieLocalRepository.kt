package com.example.data_local.repositories

import com.example.data_local.converters.MovieConverter
import com.example.data_local.database.AppDatabase
import com.example.domain.contracts.movies.MovieRepository
import com.example.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieLocalRepository(database: AppDatabase) : MovieRepository {

    private val dao = database.movieDao()

    override suspend fun listAll() =
        dao.listAll()
            .map { movies ->
                movies.map { movieEntity ->
                    MovieConverter.toDomain(movieEntity)
                }
            }

    override suspend fun getById(id: Int) =
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

    override suspend fun saveFavoriteMovie(movie: Movie): Flow<Unit> =
        dao.create(MovieConverter.toEntity(movie))
}