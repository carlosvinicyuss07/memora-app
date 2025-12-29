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
        uri?.let {
            uriToImageBitmap(
                context,
                it.toUri()
            )
        }
    }
}