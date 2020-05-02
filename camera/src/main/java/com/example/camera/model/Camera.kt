package com.example.camera.model

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.util.concurrent.ExecutorService

class Camera {
    lateinit var outputDirectory: File
    var lensFacing: Int = CameraSelector.LENS_FACING_BACK

    lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    lateinit var cameraProvider: ProcessCameraProvider
    lateinit var cameraSelector: CameraSelector
    lateinit var cameraExecutor: ExecutorService
    lateinit var preview: Preview
    lateinit var imageCapture: ImageCapture
}