package com.example.intro.ui.actions

import android.view.View
import com.example.presentation.binding.MovieBinding

interface MovieItemClick {
    fun movieClick(movie: MovieBinding)
    fun movieLongClick(view: View, movie: MovieBinding): Boolean
}