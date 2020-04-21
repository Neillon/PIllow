package com.example.presentation.di

import com.example.data_api.MovieApiBuilder
import com.example.data_api.repositories.MovieApiRepository
import com.example.data_api.services.MovieApiService
import com.example.data_local.database.AppDatabase
import com.example.domain.interactors.movies.*
import com.example.domain.repositories.movies.MovieLocalRepository
import com.example.domain.repositories.movies.MovieRemoteRepository
import com.example.presentation.viewmodels.FavoriteMovieViewModel
import com.example.presentation.viewmodels.TrendMovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    single {
        com.example.data_local.repositories.MovieLocalRepository(AppDatabase.getInstance(context = get())) as MovieLocalRepository
    }

    single {
        MovieApiBuilder.createService(serviceClass = MovieApiService::class.java)
    }

    factory {
        MovieApiRepository(service = get()) as MovieRemoteRepository
    }

    factory {
        GetByIdUseCase(localRepository = get(), remoteRepository = get())
    }

    factory {
        ListTrendingMoviesUseCase(remoteRepository = get())
    }

    factory {
        SaveFavoriteMovieUseCase(localRepository = get())
    }

    factory {
        SearchMoviesUseCase(localRepository = get())
    }

    factory {
        ListFavoriteMoviesUseCase(localRepository = get())
    }

    factory {
        DeleteMovieUseCase(localRepository = get())
    }

    viewModel {
        TrendMovieViewModel(
            listTrendingMoviesUseCase = get(),
            saveFavoriteMovieUseCase = get(),
            listFavoriteMoviesUseCase = get()
        )
    }

    viewModel {
        FavoriteMovieViewModel(
            listFavoriteMovieUseCase = get(),
            deleteMovieUseCase = get(),
            getByIdUseCase = get(),
            searchMoviesUseCase = get()
        )
    }
}
