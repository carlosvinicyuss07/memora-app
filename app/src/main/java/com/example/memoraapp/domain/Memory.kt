package com.example.memoraapp.domain

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Memory(
    val id: Int = 0,
    val title: String,
    val description: String,
    val date: LocalDate,
    val imageUri: String? = null
)

fun LocalDate.formattedToString(): String {
    val formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy")
    return this.format(formatter)
}
