package com.example.memoraapp.data

import java.time.LocalDate

data class Memory(
    val id: Int,
    val title: String,
    val description: String,
    val date: LocalDate,
    val imageUri: String? = null
)
