package com.example.presentation.viewmodels

import android.content.Context
import android.util.DisplayMetrics
import androidx.lifecycle.ViewModel
import com.example.camera.model.Camera
import com.example.camera.CameraBuilder
import com.example.camera.usecase.ICameraUseCase
import java.io.File

class CameraViewModel(
    private val cameraUseCase: ICameraUseCase
) : ViewModel() {
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
            .buildOutputDirectory(context)
            .build()
    }

    fun takePicture(context: Context, callback: (savedPhoto: File) -> Unit) = cameraUseCase.takePicture(context, camera) { callback(it) }
}