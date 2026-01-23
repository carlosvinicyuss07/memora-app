package com.example.memoraapp.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memories")
data class MemoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val dateIso: String,
    val imageUri: String?,

    @ColumnInfo(defaultValue = "")
    val categoria: String
)
