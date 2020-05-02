package com.example.camera.usecase

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import com.example.camera.model.Camera
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraUseCase : ICameraUseCase {
    override fun takePicture(
        context: Context,
        camera: Camera,
        callback: (savedPhoto: File) -> Unit
    ) {

        // Get a stable reference of the modifiable image capture use case
        camera.imageCapture?.let { imageCapture ->

            // Create output file to hold the image
            val photoFile =
                createFile(
                    camera.outputDirectory,
                    FILENAME,
                    PHOTO_EXTENSION
                )

            // Setup image capture metadata
            val metadata = ImageCapture.Metadata().apply {
                isReversedHorizontal = camera.lensFacing == CameraSelector.LENS_FACING_FRONT
            }

            // Create output options object which contains file + metadata
            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
                .setMetadata(metadata)
                .build()

            // Setup image capture listener which is triggered after photo has been taken
            imageCapture.takePicture(
                outputOptions,
                camera.cameraExecutor,
                object : ImageCapture.OnImageSavedCallback {
                    override fun onError(exc: ImageCaptureException) {
                        Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                    }

                    override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                        callback(photoFile)
                    }
                })
        }
    }

    companion object {
        private const val TAG = "CameraActivity"
        private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PHOTO_EXTENSION = ".jpg"

        private fun createFile(baseFolder: File, format: String, extension: String) =
            File(
                baseFolder, SimpleDateFormat(format, Locale.US)
                    .format(System.currentTimeMillis()) + extension
            )
    }
}