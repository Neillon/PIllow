package com.example.data_local.repositories

import com.example.data_local.converters.MovieConverter
import com.example.data_local.database.AppDatabase
import com.example.data_local.extesions.convertToDomain
import com.example.domain.repositories.movies.MovieLocalRepository
import com.example.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieLocalRepository(database: AppDatabase) : MovieLocalRepository {
    private val dao = database.movieDao()

    override suspend fun getById(id: Long): Flow<Movie?> =
        dao.getById(id).map { it?.convertToDomain() }

    override suspend fun search(query: String) =
        dao.search(query).map { it.convertToDomain() }

    override suspend fun saveFavoriteMovie(movie: Movie): Flow<Movie> {
        var id = dao.create(MovieConverter.toEntity(movie))

        return getById(id).map { it!! }
    }

    override suspend fun listFavoriteMovies() = dao.listAll().map { it.convertToDomain() }

    override suspend fun delete(movie: Movie) = dao.delete(MovieConverter.toEntity(movie))
}