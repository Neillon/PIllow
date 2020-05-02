package com.example.presentation.viewmodels

import android.content.Context
import android.util.DisplayMetrics
import androidx.camera.core.CameraSelector
import androidx.lifecycle.ViewModel
import com.example.presentation.R
import java.io.File

class CameraViewModel : ViewModel() {
    lateinit var camera: Camera

    fun initializeCameraProperties(
        context: Context,
        metrics: DisplayMetrics,
        lensFacing: Int
    ) {
        camera = CameraBuilder
            .buildCameraProvider(context)
            .buildCameraSelector(lensFacing)
            .buildCameraExecutor()
            .buildCameraPreview()
            .buildImageCapture(metrics)
            .build()
    }

    /** Use external media if it is available, our app's file directory otherwise */
    fun getOutputDirectory(context: Context): File {
        val appContext = context.applicationContext
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, appContext.resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else appContext.filesDir
    }
}