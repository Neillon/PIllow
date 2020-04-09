package com.example.intro.extensions

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.rishabhharit.roundedimageview.RoundedImageView

fun ImageView?.setDrawable(context: Context, drawable: Int) {
    this.apply {
        this?.setImageDrawable(
            ContextCompat.getDrawable(context, drawable)
        )
    }
}

fun ImageView.loadMovieImageFromPath(path: String?) {
    Glide.with(context)
        .load("https://image.tmdb.org/t/p/w500${path}")
        .centerCrop()
        .into(this)
}

fun RoundedImageView.loadMovieImageFromPath(path: String?) {
    Glide.with(context)
        .load("https://image.tmdb.org/t/p/w500${path}")
        .centerCrop()
        .into(this)
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: RoundedImageView, uri: String?) {
    view.loadMovieImageFromPath(uri)
}

@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, uri: String?) {
    view.loadMovieImageFromPath(uri)
}