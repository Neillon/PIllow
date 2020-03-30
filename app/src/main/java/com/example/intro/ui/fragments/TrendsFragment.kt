package com.example.intro.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2

import com.example.intro.R
import com.example.intro.adapters.TrendsAdapter
import com.example.intro.model.TrendMovie
import kotlinx.android.synthetic.main.fragment_trends.*
import kotlin.math.abs

class TrendsFragment : Fragment() {

    private val trendMovieAdapter = TrendsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { }

        val movies = listOf(
            TrendMovie(R.drawable.intro_kong),
            TrendMovie(R.drawable.intro_guardians),
            TrendMovie(R.drawable.intro_joker)
        )
        trendMovieAdapter.setData(movies)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
