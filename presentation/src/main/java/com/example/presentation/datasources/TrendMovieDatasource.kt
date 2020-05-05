package com.example.presentation.datasources

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.domain.interactors.NoParams
import com.example.domain.interactors.movies.ListFavoriteMoviesUseCase
import com.example.domain.interactors.movies.ListTrendingMoviesUseCase
import com.example.presentation.binding.MovieBinding
import com.example.presentation.common.ViewState
import com.example.presentation.common.asLiveData
import com.example.presentation.common.loading
import com.example.presentation.extensions.convertToBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import java.util.concurrent.ThreadPoolExecutor

class TrendMovieDatasource(
    private val listTrendingMoviesUseCase: ListTrendingMoviesUseCase,
    private val listFavoriteMoviesUseCase: ListFavoriteMoviesUseCase
) : PageKeyedDataSource<Int, MovieBinding>() {

    private val _state: MutableLiveData<ViewState> by lazy { MutableLiveData<ViewState>() }
    var state = _state.asLiveData

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
            try {
                withContext(Dispatchers.Main){
                    _state.loading()
                }
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
                        withContext(Dispatchers.Main){
                            _state.value = ViewState.Success(null)
                        }
                        loadInitialCallback?.onResult(movies, null, page)
                        callback?.onResult(movies, page)
                    }
            } catch (e: Exception) {
                _state.value = ViewState.Error(e)
                Log.e(TAG, e.localizedMessage)
            }
        }
    }

    companion object {
        internal const val TAG = "TrendMoviesDatasource"
    }
}