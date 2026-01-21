package com.example.memoraapp.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

@Composable
fun rememberImageBitmap(uri: String?): ImageBitmap? {
    val context = LocalContext.current

    return remember(uri) {
        if (uri == null) return@remember null

        try {
            uriToImageBitmap(
                context,
                uri.toUri()
            )
        } catch (e: SecurityException) {
            null
        } catch (e: Exception) {
            null
        }
    }
}