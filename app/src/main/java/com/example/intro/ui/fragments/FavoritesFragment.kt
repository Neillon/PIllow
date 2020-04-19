package com.example.intro.ui.fragments

import android.content.ClipData
import android.content.ClipDescription
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager

import com.example.intro.R
import com.example.intro.adapters.FavoriteMovieAdapter
import com.example.intro.adapters.MovieImageDragShadowBuilder
import com.example.intro.utils.extensions.exhaustive
import com.example.intro.ui.actions.MovieItemClick
import com.example.presentation.binding.MovieBinding
import com.example.presentation.common.ViewState
import com.example.presentation.viewmodels.FavoriteMovieViewModel
import com.rishabhharit.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject

class FavoritesFragment : Fragment(R.layout.fragment_favorites), MovieItemClick {

    private val viewModel: FavoriteMovieViewModel by inject()
    private val favoriteMoviesAdapter = FavoriteMovieAdapter(this)

    @ExperimentalCoroutinesApi
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

    @ExperimentalCoroutinesApi
    private fun setupLayout() {
        mRecyclerViewFavoriteMovies.apply {
            adapter = favoriteMoviesAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        mFabDeleteMovie.setOnDragListener { view, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> true
                DragEvent.ACTION_DRAG_ENTERED -> true
                DragEvent.ACTION_DRAG_LOCATION -> {
                    view.isVisible = false
                    true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    view.isVisible = false
                    true
                }
                DragEvent.ACTION_DROP -> {
                    deleteMovie(event, view)
                    true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    when (event.result) {
                        true ->
                            Log.d("FavoriteMovieFragment", "The drop was handled.")
                        else ->
                            Log.d("FavoriteMovieFragment", "The drop didn't work.")
                    }
                    view.isVisible = false
                    true
                }
                else -> {
                    view.isVisible = false
                    false
                }
            }
        }

    }

    @ExperimentalCoroutinesApi
    private fun deleteMovie(event: DragEvent?, view: View) {
        val item: ClipData.Item = event?.clipData!!.getItemAt(0)
        val dragData = item.text as String?

        favoriteMoviesAdapter.deleteMovie(dragData?.toLong() ?: 0L)
        viewModel.deleteMovie(dragData?.toLong() ?: 0L) {
            view.isVisible = false
        }
    }

    override fun movieClick(movie: MovieBinding) {
        Toast.makeText(context, "Teste", Toast.LENGTH_LONG).show()
    }

    override fun movieLongClick(view: View, movie: MovieBinding): Boolean {
        mFabDeleteMovie.isVisible = true

        val item = ClipData.Item(movie.id.toString() as? CharSequence)
        Log.d("FavoriteMovieAdapter", movie.id.toString())

        val dragData = ClipData(
            movie.id as? CharSequence,
            arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
            item
        )

        val movieImageShadow = MovieImageDragShadowBuilder(view, (view as RoundedImageView).drawable)

        view.startDrag(
            dragData,
            movieImageShadow,
            null,
            0
        )
        return true
    }
}
