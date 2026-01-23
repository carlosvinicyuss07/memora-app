package com.example.memoraapp.data.mapper

import com.example.memoraapp.data.local.MemoryEntity
import com.example.memoraapp.domain.Memory
import java.time.LocalDate

fun MemoryEntity.toDomain(): Memory =
    Memory(
        id = id,
        title = title,
        description = description,
        date = LocalDate.parse(dateIso),
        imageUri = imageUri
    )

fun Memory.toEntity(): MemoryEntity =
    MemoryEntity(
        id = id,
        title = title,
        description = description,
        dateIso = date.toString(),
        imageUri = imageUri,
        categoria = "category"
    )
