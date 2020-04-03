package com.example.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.NoParams
import com.example.domain.interactors.movies.ListTrendingMoviesUseCase
import com.example.presentation.common.ViewState
import com.example.presentation.common.asLiveData
import com.example.presentation.converters.MovieBindingConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MovieViewModel(
    private val listTrendingMoviesUseCase: ListTrendingMoviesUseCase
) : ViewModel() {

    private val _state: MutableLiveData<ViewState> by lazy { MutableLiveData<ViewState>() }
    var state = _state.asLiveData

    @ExperimentalCoroutinesApi
    fun listTrendingMovies() = viewModelScope.launch {
        _state.postValue(ViewState.Loading)
        try {
            listTrendingMoviesUseCase.execute(NoParams())
                .flowOn(Dispatchers.IO)
                .collect { movies ->
                    val moviesBinding =
                        movies.map { movie -> MovieBindingConverter.toBinding(movie) }
                    _state.postValue(ViewState.Success(moviesBinding))
                }
        } catch (e: Exception) {
            _state.postValue(ViewState.Error(error = e))
        }
    }
}