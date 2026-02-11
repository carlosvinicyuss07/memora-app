package com.example.memoraapp.presentation.ui.extensions

import com.example.memoraapp.domain.Memory
import com.example.memoraapp.presentation.ui.screens.memories.MemoryUi

fun Memory.toUi() = MemoryUi(
    id = id,
    title = title,
    description = description,
    date = date.formattedToString(),
    imageUri = imageUri
)