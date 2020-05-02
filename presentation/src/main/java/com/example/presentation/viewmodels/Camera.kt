package com.example.presentation.viewmodels

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.ExecutorService

class Camera {
    lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    lateinit var cameraProvider: ProcessCameraProvider
    lateinit var cameraSelector: CameraSelector
    lateinit var cameraExecutor: ExecutorService
    lateinit var preview: Preview
    lateinit var imageCapture: ImageCapture
}