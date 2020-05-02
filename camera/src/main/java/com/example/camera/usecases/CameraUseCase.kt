package com.example.camera.usecases

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import com.example.domain.contracts.ICameraUseCase
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.util.concurrent.ExecutorService

class CameraUseCase: ICameraUseCase {

    private var lensFacing: Int = CameraSelector.LENS_FACING_FRONT
    private lateinit var outputDirectory: File

    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var cameraSelector: CameraSelector

    /** Blocking camera operations are performed using this executor */
    private lateinit var cameraExecutor: ExecutorService

    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null

    override fun takePicture() {
        TODO("Not yet implemented")
    }

    override fun imagePreview() {
        TODO("Not yet implemented")
    }
}