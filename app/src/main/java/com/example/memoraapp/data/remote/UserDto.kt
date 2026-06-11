package com.example.memoraapp.data.remote

import com.google.firebase.Timestamp

data class UserDto(
    val fullName: String = "",
    val email: String = "",
    val photoUrl: String? = null,
    val createdAt: Timestamp? = null,
    val totalMemories: Long = 0
)
