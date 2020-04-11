package com.example.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.NoParams
import com.example.domain.interactors.movies.DeleteMovieUseCase
import com.example.domain.interactors.movies.GetByIdUseCase
import com.example.domain.interactors.movies.ListFavoriteMoviesUseCase
import com.example.presentation.common.ViewState
import com.example.presentation.common.asLiveData
import com.example.presentation.extensions.convertToBinding
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteMovieViewModel(
    private val listFavoriteMovieUseCase: ListFavoriteMoviesUseCase,
    private val getByIdUseCase: GetByIdUseCase,
    private val deleteMovieUseCase: DeleteMovieUseCase
) : ViewModel() {

    private val _state: MutableLiveData<ViewState> by lazy { MutableLiveData<ViewState>() }
    var state = _state.asLiveData

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

    fun deleteMovie(id: Long, onFinalize: () -> Unit) = viewModelScope.launch {
        getByIdUseCase.execute(GetByIdUseCase.GetByIdParams(id, searchLocal = true))
            .collect { movie ->
                movie?.let {
                    deleteMovieUseCase.execute(DeleteMovieUseCase.DeleteMovieParams(it))
                        .collect {
                            onFinalize()
                        }
                }
            }
    }
}