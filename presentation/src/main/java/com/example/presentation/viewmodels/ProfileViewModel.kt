package com.example.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interactors.NoParams
import com.example.domain.interactors.movies.ListFavoriteMoviesUseCase
import com.example.presentation.common.asLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val favoriteMoviesUseCase: ListFavoriteMoviesUseCase
) : ViewModel() {

    private val _movieCount: MutableLiveData<Int> = MutableLiveData()
    val movieCount = _movieCount.asLiveData

    fun countMovies() = viewModelScope.launch {
        favoriteMoviesUseCase.execute(NoParams())
            .flowOn(Dispatchers.IO)
            .collect { movies ->
                _movieCount.postValue(movies.size)
            }
    }
}