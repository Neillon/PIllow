package com.example.intro.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.intro.R
import com.example.intro.adapters.FavoriteMovieAdapter
import com.example.intro.extensions.exhaustive
import com.example.intro.ui.actions.MovieItemClick
import com.example.presentation.binding.MovieBinding
import com.example.presentation.common.ViewState
import com.example.presentation.viewmodels.FavoriteMovieViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.koin.android.ext.android.inject

class FavoritesFragment : Fragment(R.layout.fragment_favorites), MovieItemClick {

    private val viewModel: FavoriteMovieViewModel by inject()
    private val favoriteMoviesAdapter = FavoriteMovieAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listFavoriteMovies()
        observeViewModel()
        setupLayout()
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, Observer {
            when (it) {
                ViewState.Loading -> {
                    mRecyclerViewFavoriteMovies.isVisible = false
                    mProgressBarFavoriteMovie.isVisible = true
                    mTextViewFavoriteMovieMessage.isVisible = true
                    mTextViewFavoriteMovieMessage.text = "Loading movies"
                }
                is ViewState.Success<*> -> {
                    favoriteMoviesAdapter.setData(it.data as ArrayList<MovieBinding>)
                    mRecyclerViewFavoriteMovies.isVisible = true
                    mProgressBarFavoriteMovie.isVisible = false
                    mTextViewFavoriteMovieMessage.isVisible = false
                }
                is ViewState.Error -> {
                    mRecyclerViewFavoriteMovies.isVisible = false
                    mProgressBarFavoriteMovie.isVisible = false
                    mTextViewFavoriteMovieMessage.isVisible = true
                    mTextViewFavoriteMovieMessage.text = "Error loading movies: ${it.error.message}"
                }
            }.exhaustive
        })
    }

    private fun setupLayout() {
        mRecyclerViewFavoriteMovies.apply {
            adapter = favoriteMoviesAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    override fun movieClick(movie: MovieBinding) {
        Toast.makeText(context, "Teste", Toast.LENGTH_LONG).show()
    }
}
