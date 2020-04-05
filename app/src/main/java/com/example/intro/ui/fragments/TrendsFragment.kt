package com.example.intro.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer

import com.example.intro.R
import com.example.intro.adapters.TrendsAdapter
import com.example.presentation.common.ViewState
import com.example.presentation.viewmodels.TrendMovieViewModel
import kotlinx.android.synthetic.main.fragment_trends.*
import org.koin.android.ext.android.inject
import kotlin.math.abs

import com.example.intro.extensions.exhaustive
import com.example.intro.model.TrendMovie

class TrendsFragment : Fragment(R.layout.fragment_trends) {

    private val trendMovieAdapter = TrendsAdapter()
    private val viewModel: TrendMovieViewModel by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()

        viewModel.state.observe(viewLifecycleOwner, Observer {
            when (it) {
                ViewState.Loading -> {
                    mViewPagerTrendMovies.isVisible = false
                    mProgressBarTrendMovie.isVisible = true
                    mTextViewTrendMovieMessage.isVisible = true
                    mTextViewTrendMovieMessage.text = "Loading movies"
                }
                is ViewState.Success<*> -> {
                    trendMovieAdapter.setData(it.data as ArrayList<TrendMovie>)
                    mViewPagerTrendMovies.isVisible = true
                    mProgressBarTrendMovie.isVisible = false
                    mTextViewTrendMovieMessage.isVisible = false
                }
                is ViewState.Error -> {
                    mViewPagerTrendMovies.isVisible = false
                    mProgressBarTrendMovie.isVisible = false
                    mTextViewTrendMovieMessage.isVisible = true
                    mTextViewTrendMovieMessage.text = "Error loading movies"
                }
                else -> {
                    mViewPagerTrendMovies.isVisible = false
                    mProgressBarTrendMovie.isVisible = false
                    mTextViewTrendMovieMessage.isVisible = true
                    mTextViewTrendMovieMessage.text = "Nothing to show"
                }
            }.exhaustive
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
    }
}
