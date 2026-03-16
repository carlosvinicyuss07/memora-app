package com.example.memoraapp.data.mapper

import com.example.memoraapp.data.remote.UserDto
import com.example.memoraapp.domain.User
import com.google.firebase.Timestamp

fun UserDto.toDomain(id: String) = User(
    id = id,
    fullName = fullName,
    email = email,
    photoUrl = photoUrl,
    createdAt = createdAt?.toDate(),
    totalMemories = totalMemories.toInt()
)

fun User.toDto() = UserDto(
    fullName = fullName,
    email = email,
    photoUrl = photoUrl,
    createdAt = createdAt?.let { Timestamp(it) },
    totalMemories = totalMemories.toLong()
)