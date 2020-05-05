package com.example.intro.utils.bindingAdapters

import android.graphics.drawable.Drawable
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.example.intro.R
import com.example.intro.utils.extensions.loadMovieImageFromFilePath
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

@BindingAdapter("android:favorite")
fun favoriteIcon(view: ImageButton, favorite: Boolean) {
    if (favorite) {
        view.setBackgroundResource(R.drawable.ic_star_solid)
    } else
        view.setBackgroundResource(R.drawable.ic_star_border)
}

@BindingAdapter("android:imageFilePath")
fun loadImageFromFilePath(view: RoundedImageView, path: String?) {
    view.loadMovieImageFromFilePath(path)
}


