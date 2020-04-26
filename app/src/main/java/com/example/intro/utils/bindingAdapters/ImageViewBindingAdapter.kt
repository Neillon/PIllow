package com.example.intro.utils.bindingAdapters

import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.intro.R
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