package com.example.memoraapp.presentation.ui.util

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import java.io.File

fun copyUriToCache(context: Context, uri: Uri): String {
    val inputStream = context.contentResolver.openInputStream(uri)
        ?: throw IllegalStateException("Não foi possível abrir a URI")

    val file = File(
        context.cacheDir,
        "image_${System.currentTimeMillis()}.jpg"
    )

    file.outputStream().use { output ->
        inputStream.use { input ->
            input.copyTo(output)
        }
    }

    return file.toUri().toString()
}