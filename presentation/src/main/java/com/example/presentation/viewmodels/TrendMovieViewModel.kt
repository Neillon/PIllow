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
    private val saveFavoriteMovieUseCase: SaveFavoriteMovieUseCase
) : ViewModel() {

    private var moviesLiveData: LiveData<PagedList<MovieBinding>>
    val state by lazy {
        trendMovieDatasource.state
    }

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
            // state.value = ViewState.Error(e)
            e.printStackTrace()
        }
    }

}