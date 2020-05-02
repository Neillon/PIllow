package com.example.camera.usecase

import android.content.Context
import com.example.camera.model.Camera
import java.io.File

interface ICameraUseCase {
    fun takePicture(context: Context, camera: Camera, callback: (savedPhoto: File) -> Unit)
}