package com.example.memoraapp.ui.util

import android.util.Log
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
            Log.e("ImageLoader", "Sem permissão para acessar a URI: $uri", e)
            null
        } catch (e: Exception) {
            Log.e("ImageLoader", "Erro inesperado ao carregar $uri", e)
            null
        }
    }
}