package com.example.intro.utils.bindingAdapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.intro.utils.extensions.loadMovieImageFromPath
import com.rishabhharit.roundedimageview.RoundedImageView

@BindingAdapter("android:imageUrl")
fun loadImage(view: RoundedImageView, uri: String?) {
    view.loadMovieImageFromPath(uri)
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, uri: String?) {
    view.loadMovieImageFromPath(uri)
}
