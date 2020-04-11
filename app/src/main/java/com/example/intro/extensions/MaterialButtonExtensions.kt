package com.example.intro.extensions

import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton

@BindingAdapter("android:favorite")
fun favoriteIcon(view: MaterialButton, favorite: Boolean) {
    view.isVisible = !favorite
}