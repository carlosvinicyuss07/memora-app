package com.example.memoraapp.domain

import com.google.firebase.Timestamp
import java.util.Date

data class User(
    val id: String,
    val fullName: String,
    val email: String,
    val photoUrl: String?,
    val createdAt: Date?,
    val totalMemories: Int
)
