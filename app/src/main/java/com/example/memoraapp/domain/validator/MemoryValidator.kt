package com.example.memoraapp.domain.validator

class MemoryValidator {

    fun isValidTitle(title: String): Boolean {
        return title.isNotBlank()
    }

}