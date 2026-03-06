package com.example.memoraapp.domain

import java.time.LocalDate

data class Memory(
    val id: String = "",
    val title: String,
    val description: String,
    val date: LocalDate,
    val imageUri: String? = null
)
