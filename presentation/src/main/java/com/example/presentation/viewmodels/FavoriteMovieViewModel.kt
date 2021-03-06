package com.example.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.NoParams
import com.example.domain.interactors.movies.DeleteMovieUseCase
import com.example.domain.interactors.movies.GetByIdUseCase
import com.example.domain.interactors.movies.ListFavoriteMoviesUseCase
import com.example.domain.interactors.movies.SearchMoviesUseCase
import com.example.presentation.common.ViewState
import com.example.presentation.common.asLiveData
import com.example.presentation.extensions.convertToBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteMovieViewModel(
    private val listFavoriteMovieUseCase: ListFavoriteMoviesUseCase,
    private val getByIdUseCase: GetByIdUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : ViewModel() {

    private val _state: MutableLiveData<ViewState> by lazy { MutableLiveData<ViewState>() }
    var state = _state.asLiveData

    @ExperimentalCoroutinesApi
    fun listFavoriteMovies() = viewModelScope.launch {
        _state.postValue(ViewState.Loading)
        try {
            listFavoriteMovieUseCase.execute(NoParams())
                .flowOn(IO)
                .collect { movies ->
                    _state.postValue(ViewState.Success(movies.convertToBinding()))
                }
        } catch (e: Exception) {
            _state.postValue(ViewState.Error(error = e))
            e.printStackTrace()
        }
    }

    @ExperimentalCoroutinesApi
    fun deleteMovie(id: Long, onComplete: () -> Unit) = viewModelScope.launch {
        getByIdUseCase.execute(GetByIdUseCase.GetByIdParams(id, searchLocal = true))
            .flowOn(IO)
            .collect { movie ->
                movie?.let {
                    deleteMovieUseCase.execute(DeleteMovieUseCase.DeleteMovieParams(it))
                    onComplete()
                }
            }
    }

    fun searchMoviesByTitle(query: String) = viewModelScope.launch {
        _state.postValue(ViewState.Loading)
        try {
            searchMoviesUseCase.execute(SearchMoviesUseCase.SearchMovieParams(query))
                .flowOn(IO)
                .collect { movies ->
                    _state.postValue(ViewState.Success(movies.convertToBinding()))
                }
        } catch (e: Exception) {
            _state.postValue(ViewState.Error(error = e))
            e.printStackTrace()
        }
    }
}