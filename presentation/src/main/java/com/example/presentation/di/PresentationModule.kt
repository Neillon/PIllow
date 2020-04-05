package com.example.presentation.di

import com.example.data_local.database.AppDatabase
import com.example.domain.interactors.movies.GetByIdUseCase
import com.example.domain.interactors.movies.ListTrendingMoviesUseCase
import com.example.domain.interactors.movies.SaveFavoriteMovieUseCase
import com.example.domain.interactors.movies.SearchMoviesUseCase
import com.example.domain.repositories.movies.MovieLocalRepository
import com.example.presentation.viewmodels.TrendMovieViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    single {
        com.example.data_local.repositories.MovieLocalRepository(AppDatabase.getInstance(context = get())) as MovieLocalRepository
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
        SearchMoviesUseCase(localRepository = get(), remoteRepository = get())
    }

    viewModel {
        TrendMovieViewModel(listTrendingMoviesUseCase = get())
    }
}