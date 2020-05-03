package com.example.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.domain.interactors.NoParams
import com.example.domain.interactors.movies.DeleteMovieUseCase
import com.example.domain.interactors.movies.ListFavoriteMoviesUseCase
import com.example.domain.interactors.movies.ListTrendingMoviesUseCase
import com.example.domain.interactors.movies.SaveFavoriteMovieUseCase
import com.example.presentation.binding.MovieBinding
import com.example.presentation.common.ViewState
import com.example.presentation.common.asLiveData
import com.example.presentation.converters.MovieBindingConverter
import com.example.presentation.datasources.TrendMovieDatasource
import com.example.presentation.extensions.convertToBinding
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrendMovieViewModel(
    private val trendMovieDatasource: TrendMovieDatasource,
    private val listTrendingMoviesUseCase: ListTrendingMoviesUseCase,
    private val saveFavoriteMovieUseCase: SaveFavoriteMovieUseCase,
    private val listFavoriteMoviesUseCase: ListFavoriteMoviesUseCase
) : ViewModel() {

    private val _state: MutableLiveData<ViewState> by lazy { MutableLiveData<ViewState>() }
    var state = _state.asLiveData

    var moviesLiveData: LiveData<PagedList<MovieBinding>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize( 10 )
            .setEnablePlaceholders(true)
            .setPrefetchDistance(2)
            .build()

        moviesLiveData  = initializedPagedListBuilder(config).build()
    }

    fun getMovies():LiveData<PagedList<MovieBinding>> = moviesLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, MovieBinding> {

        val dataSourceFactory = object : DataSource.Factory<Int, MovieBinding>() {
            override fun create(): DataSource<Int, MovieBinding> {
                return trendMovieDatasource
            }
        }
        return LivePagedListBuilder<Int, MovieBinding>(dataSourceFactory, config)
    }

    @ExperimentalCoroutinesApi
    fun listTrendingMovies() = viewModelScope.launch {
        _state.postValue(ViewState.Loading)
        try {
            _state.postValue(ViewState.Success<PagedList<MovieBinding>?>(moviesLiveData.value))

        } catch (e: Exception) {
            _state.postValue(ViewState.Error(error = e))
            e.printStackTrace()
        }
    }

    @ExperimentalCoroutinesApi
    private fun findFavoriteMoviesAndPostValue(movies: List<MovieBinding>) = viewModelScope.launch {
        listFavoriteMoviesUseCase.execute(NoParams())
            .flowOn(IO)
            .collect { favoriteMovies ->
                movies.map { movie ->
                    val favoriteMovie =
                        favoriteMovies.firstOrNull { favoriteMovie -> movie.movieId == favoriteMovie.movieId }

                    if (favoriteMovie != null) {
                        movie.favorite = true
                        movie.id = favoriteMovie.id
                    }
                }
                _state.postValue(ViewState.Success(movies))
            }
    }

    @ExperimentalCoroutinesApi
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