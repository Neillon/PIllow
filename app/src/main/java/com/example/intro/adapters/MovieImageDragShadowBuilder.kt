package com.example.intro.adapters

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View


class MovieImageDragShadowBuilder(val v: View, val drawable: Drawable?) : View.DragShadowBuilder(v) {

    private val shadow = drawable ?: ColorDrawable(Color.LTGRAY)

    override fun onProvideShadowMetrics(outShadowSize: Point, outShadowTouchPoint: Point) {
        val shadowWidth: Int = v.width / 2
        val shadowHeight: Int = v.height / 2

        shadow.setBounds(0, 0, shadowWidth, shadowHeight)

        outShadowSize.set(shadowWidth, shadowHeight) // set size of shadow
        outShadowTouchPoint.set(shadowWidth / 2, shadowHeight / 2) // set start drag position to the middle of shadow
    }

    override fun onDrawShadow(canvas: Canvas) {
        super.onDrawShadow(canvas)
        // canvas.scale(2.0F, 2.0F)
        shadow.draw(canvas)
        shadow.setBounds(0, 0, v.width, v.height)
    }
}