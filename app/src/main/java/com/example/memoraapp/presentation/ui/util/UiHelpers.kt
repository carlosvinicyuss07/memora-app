package com.example.memoraapp.presentation.ui.util

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.content.ContextCompat
import java.io.File
import java.io.IOException

fun uriToImageBitmap(context: Context, uri: Uri): ImageBitmap? {
    return try {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val src = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(src)
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }
        bitmap.asImageBitmap()
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

fun capturePhoto(
    imageCapture: ImageCapture,
    context: Context,
    onResult: (Uri) -> Unit
) {
    val file = File(
        context.cacheDir, "photo_${System.currentTimeMillis()}.jpg"
    )

    val options = ImageCapture.OutputFileOptions.Builder(file).build()

    imageCapture.takePicture(
        options,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(result: ImageCapture.OutputFileResults) {
                onResult(Uri.fromFile(file))
            }

            override fun onError(exception: ImageCaptureException) {
                exception.printStackTrace()
            }
        }
    )
}
