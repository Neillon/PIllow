package com.example.intro.utils.extensions

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
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
        .fitCenter()
        .into(this)
}

fun RoundedImageView.loadMovieImageFromFilePath(path: String?) {
    Glide.with(context)
        .load(path)
        .centerCrop()
        .into(this)
}