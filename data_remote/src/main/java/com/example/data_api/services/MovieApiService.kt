package com.example.data_api.services

import com.example.data_api.Response
import com.example.data_api.schemas.MovieSchema
import com.example.domain.entities.Movie
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {
    @GET("trending/movie/week")
    suspend fun listTrendingMovies(): Response<List<MovieSchema>>

    @GET("/movie/{movie_id}")
    suspend fun getById(@Path("movie_id") movieId: Int): Response<MovieSchema>

    @GET("search/movie?include_adult=false&page=1")
    suspend fun search(@Query("query") query: String): Response<List<MovieSchema>>
}