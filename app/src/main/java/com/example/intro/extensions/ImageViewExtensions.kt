package com.example.intro.extensions

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat

fun ImageView?.setDrawable(context: Context, drawable: Int) {
    this.apply {
        this?.setImageDrawable(
            ContextCompat.getDrawable(context, drawable)
        )
    }
}
