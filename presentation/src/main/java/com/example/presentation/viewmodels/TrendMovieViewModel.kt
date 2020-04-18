package com.example.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.NoParams
import com.example.domain.interactors.movies.DeleteMovieUseCase
import com.example.domain.interactors.movies.ListFavoriteMoviesUseCase
import com.example.domain.interactors.movies.ListTrendingMoviesUseCase
import com.example.domain.interactors.movies.SaveFavoriteMovieUseCase
import com.example.presentation.binding.MovieBinding
import com.example.presentation.common.ViewState
import com.example.presentation.common.asLiveData
import com.example.presentation.converters.MovieBindingConverter
import com.example.presentation.extensions.convertToBinding
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrendMovieViewModel(
    private val listTrendingMoviesUseCase: ListTrendingMoviesUseCase,
    private val saveFavoriteMovieUseCase: SaveFavoriteMovieUseCase,
    private val listFavoriteMoviesUseCase: ListFavoriteMoviesUseCase
) : ViewModel() {

    private val _state: MutableLiveData<ViewState> by lazy { MutableLiveData<ViewState>() }
    var state = _state.asLiveData

    @ExperimentalCoroutinesApi
    fun listTrendingMovies() = viewModelScope.launch {
        _state.postValue(ViewState.Loading)
        try {
            listTrendingMoviesUseCase.execute(NoParams())
                .flowOn(IO)
                .collect { movies ->
                    val moviesBinding = movies.convertToBinding()

                    findFavoriteMoviesAndPostValue(movies = moviesBinding)
                }
        } catch (e: Exception) {
            _state.postValue(ViewState.Error(error = e))
            e.printStackTrace()
        }
    }

    private fun findFavoriteMoviesAndPostValue(movies: List<MovieBinding>) = viewModelScope.launch {
        listFavoriteMoviesUseCase.execute(NoParams())
            .flowOn(IO)
            .collect { favoriteMovies ->
                movies.map { movie ->
                    val favoriteMovie =
                        favoriteMovies.firstOrNull { favoriteMovie -> movie.movieId == favoriteMovie.movieId }

                    if (favoriteMovie != null) {
                        movie.favorite = true
                        movie.id = favoriteMovie.id ?: null
                    }
                }
                _state.postValue(ViewState.Success(movies))
            }
    }

    fun favoriteMovie(movie: MovieBinding) = viewModelScope.launch {
        try {
            withContext(IO) {
                saveFavoriteMovieUseCase.execute(
                    SaveFavoriteMovieUseCase.FavoriteMovieParams(
                        MovieBindingConverter.toDomain(movie)
                    )
                )
            }
        } catch (e: Exception) {
            _state.postValue(ViewState.Error(e))
            e.printStackTrace()
        }
    }

}