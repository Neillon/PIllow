package com.example.presentation.viewmodels

import android.content.Context
import android.util.DisplayMetrics
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private const val RATIO_4_3_VALUE = 4.0 / 3.0
private const val RATIO_16_9_VALUE = 16.0 / 9.0

object CameraBuilder {

    private val camera = Camera()

    fun buildCameraProvider(context: Context): CameraBuilder {
        camera.cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        camera.cameraProvider = camera.cameraProviderFuture.get()
        return this
    }

    fun buildCameraSelector(lensFacing: Int): CameraBuilder {
        camera.cameraSelector = CameraSelector.Builder()
            .requireLensFacing(lensFacing)
            .build()
        return this
    }

    fun buildCameraPreview(): CameraBuilder {
        camera.preview = Preview.Builder().build()
        return this
    }

    fun buildCameraExecutor(): CameraBuilder {
        camera.cameraExecutor = Executors.newSingleThreadExecutor()
        return this
    }

    fun buildImageCapture(metrics: DisplayMetrics): CameraBuilder {
        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)

        camera.imageCapture = ImageCapture.Builder()
            .setFlashMode(ImageCapture.FLASH_MODE_AUTO)
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .setTargetAspectRatio(screenAspectRatio!!)
            .build()
        return this
    }

    fun build(): Camera = camera

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE))
            return AspectRatio.RATIO_4_3

        return AspectRatio.RATIO_16_9
    }
}
