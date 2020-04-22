package com.example.intro.ui.fragments

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.intro.R
import com.example.intro.databinding.FavoriteMovieItemContainerBinding
import com.example.intro.databinding.FragmentMovieDetailBinding
import com.example.intro.utils.extensions.loadMovieImageFromPath
import com.example.presentation.binding.MovieBinding
import kotlinx.android.synthetic.main.fragment_movie_detail.*

class MovieDetailFragment : Fragment() {

    private lateinit var movie: MovieBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            movie = it.get("movie") as MovieBinding
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            (it as AppCompatActivity).setSupportActionBar(mToolbarDetailFragment)
            NavigationUI.setupActionBarWithNavController(it, findNavController())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setupStatusBar()

        val view = DataBindingUtil.inflate<FragmentMovieDetailBinding>(
            inflater,
            R.layout.fragment_movie_detail,
            container,
            false
        )
        view.movie = movie

        return view.root
    }

    private fun setupStatusBar() {
        activity?.window?.decorView?.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        activity?.window?.statusBarColor = Color.TRANSPARENT
    }

}
