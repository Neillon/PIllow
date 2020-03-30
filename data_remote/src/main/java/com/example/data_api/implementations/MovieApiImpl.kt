package com.example.data_api.implementations

import com.example.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiImpl {

    @GET("trending/movie/week")
    suspend fun listTrendingMovies(): Flow<List<Movie>>

    @GET("/movie/{movie_id}")
    suspend fun getById(@Path("movie_id") movieId: Int): Flow<Movie>

    @GET("search/movie?include_adult=false&page=1")
    suspend fun search(@Query("query") query: String): Flow<List<Movie>>
}