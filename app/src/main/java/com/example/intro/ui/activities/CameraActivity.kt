package com.example.intro.ui.activities

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.intro.R
import com.example.presentation.viewmodels.CameraViewModel
import kotlinx.android.synthetic.main.activity_camera.*
import org.koin.android.ext.android.inject

class CameraActivity : AppCompatActivity(), LifecycleOwner {

    private val viewModel: CameraViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        mCameraPreview.post {
            val metrics = DisplayMetrics().also { mCameraPreview.display.getRealMetrics(it) }
            viewModel.initializeCameraProperties(this@CameraActivity, metrics, CameraSelector.LENS_FACING_FRONT)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                updateCameraUi()
            bindCameraUseCases()
        }
    }

    private fun bindCameraUseCases() {
        viewModel.camera.cameraProviderFuture.addListener(Runnable {
            val metrics = DisplayMetrics().also { mCameraPreview.display.getRealMetrics(it) }
            viewModel.initializeCameraProperties(
                this@CameraActivity,
                metrics,
                viewModel.camera.lensFacing
            )

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
            viewModel.takePicture(this@CameraActivity) {
                it?.let {
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

                    // TODO(Finish activity for result)
                    // finishActivity()
                }
            }
        }

        mImageButtonSwitchCamera.setOnClickListener {
            viewModel.camera.lensFacing =
                if (CameraSelector.LENS_FACING_FRONT == viewModel.camera.lensFacing) CameraSelector.LENS_FACING_BACK
                else CameraSelector.LENS_FACING_FRONT
            bindCameraUseCases()
        }
    }

    companion object {
        private const val TAG = "CameraActivity"
        private const val ANIMATION_SLOW_MILLIS = 500L
        private const val ANIMATION_FAST_MILLIS = 200L
    }
}
