package com.example.memoraapp.data.mapper

import com.example.memoraapp.data.local.MemoryEntity
import com.example.memoraapp.data.remote.MemoryDto
import com.example.memoraapp.domain.Memory
import java.time.LocalDate

fun MemoryEntity.toDomain() = Memory(
    id = id,
    title = title,
    description = description,
    date = LocalDate.parse(date),
    imageUri = imageUri
)

fun Memory.toEntity() = MemoryEntity(
    id = id,
    title = title,
    description = description,
    date = date.toString(),
    imageUri = imageUri
)

fun MemoryDto.toDomain(id: String) = Memory(
    id = id,
    title = title,
    description = description,
    date = LocalDate.parse(date),
    imageUri = imageUrl
)

fun Memory.toDto() = MemoryDto(
    title = title,
    description = description,
    date = date.toString(),
    imageUrl = imageUri.toString()
)