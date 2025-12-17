package com.example.memoraapp.domain

import com.example.memoraapp.ui.screens.memories.MemoryUi

fun Memory.toUi() = MemoryUi(
    id = id,
    title = title,
    description = description,
    date = date.toString(),
    imageUri = imageUri
)