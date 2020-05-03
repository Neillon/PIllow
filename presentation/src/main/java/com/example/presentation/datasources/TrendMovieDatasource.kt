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
import kotlinx.coroutines.flow.zip
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
                .zip(listFavoriteMoviesUseCase.execute(NoParams())) { moviesRemote, moviesLocal ->
                    val moviesBinding = moviesRemote.convertToBinding()
                    moviesBinding.map { movie ->
                        val favoriteMovie =
                            moviesLocal.firstOrNull { favoriteMovie -> movie.movieId == favoriteMovie.movieId }

                        if (favoriteMovie != null) {
                            movie.favorite = true
                            movie.id = favoriteMovie.id
                        }
                    }
                    moviesBinding
                }
                .flowOn(Dispatchers.IO)
                .collect { movies ->
                    loadInitialCallback?.onResult(movies, null, page)
                    callback?.onResult(movies, page)
                }
        }
    }
}