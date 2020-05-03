package com.example.presentation.datasources

import androidx.paging.PageKeyedDataSource
import com.example.domain.interactors.NoParams
import com.example.domain.interactors.movies.ListFavoriteMoviesUseCase
import com.example.domain.interactors.movies.ListTrendingMoviesUseCase
import com.example.presentation.binding.MovieBinding
import com.example.presentation.extensions.convertToBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class TrendMovieDatasource(
    private val listTrendingMoviesUseCase: ListTrendingMoviesUseCase,
    private val listFavoriteMoviesUseCase: ListFavoriteMoviesUseCase
) : PageKeyedDataSource<Int, MovieBinding>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MovieBinding>
    ) {
        requestMovies(1, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieBinding>) {
        requestMovies(params.key + 1, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieBinding>) {}

    private fun requestMovies(
        page: Int,
        loadInitialCallback: LoadInitialCallback<Int, MovieBinding>?,
        callback: LoadCallback<Int, MovieBinding>?
    ) {
        GlobalScope.launch {
            listTrendingMoviesUseCase.execute(
                ListTrendingMoviesUseCase.ListTrendingMoviesParams(page = page)
            )
                .flowOn(Dispatchers.IO)
                .collect { movies ->
                    val moviesBinding = movies.convertToBinding()

                    loadInitialCallback?.onResult(moviesBinding, null, page)
                    callback?.onResult(moviesBinding, page)

                    // mergeWithFavoriteMovies(page, loadInitialCallback, callback, moviesBinding)
                }
        }
    }

    @ExperimentalCoroutinesApi
    private fun mergeWithFavoriteMovies(
        page: Int,
        loadInitialCallback: LoadInitialCallback<Int, MovieBinding>?,
        callback: LoadCallback<Int, MovieBinding>?,
        movies: List<MovieBinding>
    ) = GlobalScope.launch {
        listFavoriteMoviesUseCase.execute(NoParams())
            .flowOn(Dispatchers.IO)
            .collect { favoriteMovies ->
                movies.map { movie ->
                    val favoriteMovie =
                        favoriteMovies.firstOrNull { favoriteMovie -> movie.movieId == favoriteMovie.movieId }

                    if (favoriteMovie != null) {
                        movie.favorite = true
                        movie.id = favoriteMovie.id
                    }
                }

                loadInitialCallback?.onResult(movies, null, page)
                callback?.onResult(movies, page)
            }
    }
}