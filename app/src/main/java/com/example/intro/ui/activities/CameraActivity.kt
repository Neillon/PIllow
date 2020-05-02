package com.example.intro.ui.activities

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.Camera
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.core.ImageCapture.Metadata
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import androidx.lifecycle.LifecycleOwner
import com.example.intro.R
import com.example.presentation.viewmodels.CameraViewModel
import kotlinx.android.synthetic.main.activity_camera.*
import org.koin.android.ext.android.inject
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CameraActivity : AppCompatActivity(), LifecycleOwner {

    private val viewModel: CameraViewModel by inject()

    private var lensFacing: Int = CameraSelector.LENS_FACING_FRONT
    private lateinit var outputDirectory: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        outputDirectory = viewModel.getOutputDirectory(this@CameraActivity)

        mCameraPreview.post {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                updateCameraUi()
            bindCameraUseCases()
        }
    }

    private fun bindCameraUseCases() {
        viewModel.camera.cameraProviderFuture.addListener(Runnable {
            val metrics = DisplayMetrics().also { mCameraPreview.display.getRealMetrics(it) }
            viewModel.initializeCameraProperties(this@CameraActivity, metrics, lensFacing)

            viewModel.camera.cameraProvider.unbindAll()

            try {
                val camera = viewModel.camera.cameraProvider.bindToLifecycle(
                    this@CameraActivity,
                    viewModel.camera.cameraSelector,
                    viewModel.camera.preview,
                    viewModel.camera.imageCapture
                )

                viewModel.camera.preview?.setSurfaceProvider(
                    mCameraPreview.createSurfaceProvider(
                        camera?.cameraInfo
                    )
                )
            } catch (e: Exception) {
                Log.e(TAG, "Use case binding failed", e)
            }
        }, ContextCompat.getMainExecutor(this@CameraActivity))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateCameraUi() {
        mCameraPreview?.let {
            mCameraPreview.removeView(it)
        }

        // Listener for button used to capture photo
        mImageButtonTakePicture.setOnClickListener {

            // Get a stable reference of the modifiable image capture use case
            viewModel.camera.imageCapture?.let { imageCapture ->

                // Create output file to hold the image
                val photoFile = createFile(outputDirectory, FILENAME, PHOTO_EXTENSION)

                // Setup image capture metadata
                val metadata = Metadata().apply {
                    // Mirror image when using the front camera
                    isReversedHorizontal = lensFacing == CameraSelector.LENS_FACING_FRONT
                }

                // Create output options object which contains file + metadata
                val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
                    .setMetadata(metadata)
                    .build()

                // Setup image capture listener which is triggered after photo has been taken
                imageCapture.takePicture(
                    outputOptions,
                    viewModel.camera.cameraExecutor,
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onError(exc: ImageCaptureException) {
                            Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                        }

                        override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                            val savedUri = output.savedUri ?: Uri.fromFile(photoFile)
                            Log.d(TAG, "Photo capture succeeded: $savedUri")

                            // Implicit broadcasts will be ignored for devices running API level >= 24
                            // so if you only target API level 24+ you can remove this statement
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
                                sendBroadcast(Intent(Camera.ACTION_NEW_PICTURE, savedUri))

                            // If the folder selected is an external media directory, this is
                            // unnecessary but otherwise other apps will not be able to access our
                            // images unless we scan them using [MediaScannerConnection]
                            val mimeType = MimeTypeMap.getSingleton()
                                .getMimeTypeFromExtension(savedUri.toFile().extension)
                            MediaScannerConnection.scanFile(
                                this@CameraActivity,
                                arrayOf(savedUri.toFile().absolutePath),
                                arrayOf(mimeType)
                            ) { _, uri ->
                                Log.d(TAG, "Image capture scanned into media store: $uri")
                            }
                        }
                    })

                // We can only change the foreground Drawable using API level 23+ API
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    // Display flash animation to indicate that photo was captured
                    mCameraPreview.postDelayed({
                        mCameraPreview.foreground = ColorDrawable(Color.WHITE)
                        mCameraPreview.postDelayed(
                            { mCameraPreview.foreground = null }, ANIMATION_FAST_MILLIS
                        )
                    }, ANIMATION_SLOW_MILLIS)
                }
            }
        }

        mImageButtonSwitchCamera.setOnClickListener {
            lensFacing =
                if (CameraSelector.LENS_FACING_FRONT == lensFacing) CameraSelector.LENS_FACING_BACK
                else CameraSelector.LENS_FACING_FRONT
            bindCameraUseCases()
        }
    }

    companion object {
        private const val TAG = "CameraActivity"
        private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PHOTO_EXTENSION = ".jpg"
        private const val ANIMATION_SLOW_MILLIS = 500L
        private const val ANIMATION_FAST_MILLIS = 200L

        private fun createFile(baseFolder: File, format: String, extension: String) =
            File(
                baseFolder, SimpleDateFormat(format, Locale.US)
                    .format(System.currentTimeMillis()) + extension
            )
    }
}
