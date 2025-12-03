package com.example.memoraapp.domain

import com.example.memoraapp.domain.Memory
import kotlinx.coroutines.flow.Flow

interface MemoryRepository {
    fun getAllMemories(): Flow<List<Memory>>
    suspend fun insert(memory: Memory)
    suspend fun update(memory: Memory)
    suspend fun delete(memoryId: Int)
    suspend fun getById(id: Int): Memory?
}