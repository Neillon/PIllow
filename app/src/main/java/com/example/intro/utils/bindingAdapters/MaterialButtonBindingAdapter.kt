package com.example.intro.utils.bindingAdapters

import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton

@BindingAdapter("android:favorite")
fun favoriteIcon(view: MaterialButton, favorite: Boolean) {
    view.isVisible = !favorite
}