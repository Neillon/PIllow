package com.example.intro.ui.fragments

import android.app.SearchManager
import android.content.ClipData
import android.content.ClipDescription
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.intro.R
import com.example.intro.adapters.FavoriteMovieAdapter
import com.example.intro.adapters.MovieImageDragShadowBuilder
import com.example.intro.ui.actions.MovieItemClick
import com.example.intro.ui.activities.MovieDetailActivity
import com.example.intro.utils.extensions.exhaustive
import com.example.presentation.binding.MovieBinding
import com.example.presentation.common.ViewState
import com.example.presentation.viewmodels.FavoriteMovieViewModel
import com.rishabhharit.roundedimageview.RoundedImageView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class FavoritesFragment : Fragment(R.layout.fragment_favorites), MovieItemClick {

    private val viewModel: FavoriteMovieViewModel by inject()
    private val favoriteMoviesAdapter = FavoriteMovieAdapter(this)
    private lateinit var navController: NavController

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        viewModel.listFavoriteMovies()
        observeViewModel()
        setupLayout()
    }

    // TODO("Search by voice feature - make searchview activity in another activity")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_favorite_movies, menu)

        val menuItem: MenuItem = menu.findItem(R.id.mMenuItemSearchMovie)
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menuItem.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.setIconifiedByDefault(false)

        menuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                activity?.mBottomNavigation?.isVisible = false
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                // TODO("Add logic to show the bottomNavigation after the keyboard dismiss")
                activity?.mBottomNavigation?.isVisible = true
                return true
            }
        })

        setupSearchView(searchView)
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchMoviesByTitle(newText)
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchMoviesByTitle(query)
                return true
            }
        })
    }

    @ExperimentalCoroutinesApi
    private fun setupLayout() {

        (activity as AppCompatActivity).setSupportActionBar(mToolbarFavoriteFragment)
        setHasOptionsMenu(true)

        mRecyclerViewFavoriteMovies.apply {
            adapter = favoriteMoviesAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        mFabDeleteMovie.setOnDragListener { view, event ->
            when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> true
                DragEvent.ACTION_DRAG_ENTERED -> true
                DragEvent.ACTION_DRAG_LOCATION -> true
                DragEvent.ACTION_DRAG_EXITED -> true
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
                    val data = it.data as ArrayList<MovieBinding>
                    favoriteMoviesAdapter.setData(data)
                    if (data.size == 0) {
                        mRecyclerViewFavoriteMovies.isVisible = false
                        mProgressBarFavoriteMovie.isVisible = false
                        mTextViewFavoriteMovieMessage.isVisible = true
                        mTextViewFavoriteMovieMessage.text = "Nothing to show"
                    } else {
                        mRecyclerViewFavoriteMovies.isVisible = true
                        mProgressBarFavoriteMovie.isVisible = false
                        mTextViewFavoriteMovieMessage.isVisible = false
                    }
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
    private fun deleteMovie(event: DragEvent?, view: View) {
        val item: ClipData.Item = event?.clipData!!.getItemAt(0)
        val dragData = item.text as String?

        favoriteMoviesAdapter.deleteMovie(dragData?.toLong() ?: 0L)
        viewModel.deleteMovie(dragData?.toLong() ?: 0L) {
            view.isVisible = false
        }
    }

    override fun movieClick(movie: MovieBinding) {
        val intent = Intent(activity, MovieDetailActivity::class.java)
        intent.putExtra("movie", movie)
        startActivity(intent)
    }

    override fun movieLongClick(view: View, movie: MovieBinding): Boolean {
        mFabDeleteMovie.isVisible = true

        val item = ClipData.Item(movie.id.toString() as? CharSequence)

        val dragData = ClipData(
            movie.id as? CharSequence,
            arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
            item
        )

        val movieImageShadow =
            MovieImageDragShadowBuilder(view, (view as RoundedImageView).drawable)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.startDragAndDrop(dragData, movieImageShadow, null, 0)
        } else {
            view.startDrag(dragData, movieImageShadow, null, 0)
        }

        return true
    }
}
