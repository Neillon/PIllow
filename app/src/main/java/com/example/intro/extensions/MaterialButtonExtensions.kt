package com.example.intro.extensions

import android.view.View
import androidx.databinding.BindingAdapter
import com.example.intro.R
import com.example.presentation.binding.MovieBinding
import com.google.android.material.button.MaterialButton

@BindingAdapter("android:favorite")
fun favoriteIcon(view: MaterialButton, favorite: Boolean) {
    view.setIconResource(if (favorite) R.drawable.ic_star_solid else R.drawable.ic_star_border)
}