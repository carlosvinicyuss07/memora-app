package com.example.memoraapp.ui.util

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import java.io.IOException
import java.time.LocalDate
import java.util.Calendar

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

fun showDatePickerDialog(
    context: Context,
    initial: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val cal = Calendar.getInstance().apply {
        set(initial.year, initial.monthValue - 1, initial.dayOfMonth)
    }

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selected = LocalDate.of(year, month + 1, dayOfMonth)
            onDateSelected(selected)
        },
        cal.get(Calendar.YEAR),
        cal.get(Calendar.MONTH),
        cal.get(Calendar.DAY_OF_MONTH)
    ).show()
}