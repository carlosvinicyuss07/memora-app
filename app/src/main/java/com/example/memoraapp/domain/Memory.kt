package com.example.memoraapp.domain

import java.time.LocalDate

data class Memory(
    val id: Int = 0,
    val title: String,
    val description: String,
    val date: LocalDate,
    val imageUri: String? = null
)
