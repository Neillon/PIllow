package com.example.intro.ui.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.intro.R
import com.example.intro.adapters.TrendsAdapter
import com.example.intro.utils.extensions.exhaustive
import com.example.intro.utils.extensions.isConnected
import com.example.intro.ui.actions.FavoriteMovieClick
import com.example.intro.ui.actions.MovieItemClick
import com.example.intro.ui.activities.MovieDetailActivity
import com.example.presentation.binding.MovieBinding
import com.example.presentation.common.ViewState
import com.example.presentation.viewmodels.TrendMovieViewModel
import kotlinx.android.synthetic.main.fragment_trends.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import kotlin.math.abs

class TrendsFragment : Fragment(R.layout.fragment_trends), FavoriteMovieClick, MovieItemClick {

    private val trendMovieAdapter = TrendsAdapter(this, this)
    private val viewModel: TrendMovieViewModel by inject()

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()

        if (!context?.isConnected!!) {
            mViewPagerTrendMovies.isVisible = false
            mProgressBarTrendMovie.isVisible = false
            mTextViewTrendMovieMessage.isVisible = true
            mTextViewTrendMovieMessage.text = "Error loading movies: No internet connection"
        }

        observeViewModel();
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Loading -> {
                    mViewPagerTrendMovies.isVisible = false
                    mProgressBarTrendMovie.isVisible = true
                    mTextViewTrendMovieMessage.isVisible = true
                    mTextViewTrendMovieMessage.text = "Loading movies"
                }
                is ViewState.Error -> {
                    mViewPagerTrendMovies.isVisible = false
                    mProgressBarTrendMovie.isVisible = false
                    mTextViewTrendMovieMessage.isVisible = true
                    mTextViewTrendMovieMessage.text = "Error loading movies: ${it.error.message}"
                }
                is ViewState.Success<*> -> {
                    mViewPagerTrendMovies.isVisible = true
                    mProgressBarTrendMovie.isVisible = false
                    mTextViewTrendMovieMessage.isVisible = false
                }
            }
        })

        viewModel.getMovies().observe(viewLifecycleOwner, Observer {
            trendMovieAdapter.submitList(it as PagedList<MovieBinding>)
            Log.d(TAG, "Find ${it.size} movies")
        })
    }

    private fun setupViewPager() {
        mViewPagerTrendMovies.adapter = trendMovieAdapter
        mViewPagerTrendMovies.clipToPadding = false
        mViewPagerTrendMovies.clipChildren = false
        mViewPagerTrendMovies.offscreenPageLimit = 3
        mViewPagerTrendMovies.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(8))
        compositePageTransformer.addTransformer { page, position ->
            val factor: Float = 1 - abs(position)
            page.scaleY = 0.85f + factor * 0.15f
        }

        mViewPagerTrendMovies.setPageTransformer(compositePageTransformer)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mViewPagerTrendMovies.setOnScrollChangeListener { view, scrollX, scrollY, oldScrollX, oldScrollY ->
                Toast.makeText(context, "scroll: $scrollX", Toast.LENGTH_LONG).show()
            }
        }
    }

    @ExperimentalCoroutinesApi
    override fun favoriteMovieClick(movie: MovieBinding) {
        viewModel.favoriteMovie(movie)
        trendMovieAdapter.favoriteMovie(movie)
    }

    override fun movieClick(movie: MovieBinding) {
        val intentDetail = Intent(activity, MovieDetailActivity::class.java).apply {
            putExtra("movie", movie)
        }
        startActivity(intentDetail)
    }

    override fun movieLongClick(view: View, movie: MovieBinding): Boolean {
        return true
    }

    companion object {
        internal const val TAG = "TrendsFragment"
    }
}
