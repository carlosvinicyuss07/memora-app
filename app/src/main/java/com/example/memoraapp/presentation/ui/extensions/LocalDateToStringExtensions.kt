package com.example.memoraapp.presentation.ui.extensions

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.formattedToString(): String {
    val formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy")
    return this.format(formatter)
}