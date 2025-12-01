package com.example.memoraapp.data.repository

import com.example.memoraapp.data.Memory
import kotlinx.coroutines.flow.Flow

interface MemoryRepository {
    fun getAllMemories(): Flow<List<Memory>>
    suspend fun insert(memory: Memory)
    suspend fun update(memory: Memory)
    suspend fun delete(memory: Memory)
    suspend fun getById(id: Int): Memory?
}
