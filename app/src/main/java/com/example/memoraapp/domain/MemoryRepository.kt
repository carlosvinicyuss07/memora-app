package com.example.memoraapp.domain

import kotlinx.coroutines.flow.Flow

interface MemoryRepository {
    fun getAllMemories(): Flow<List<Memory>>
    suspend fun insert(memory: Memory)
    suspend fun update(memory: Memory)
    suspend fun delete(memoryId: String)
    suspend fun getMemoryById(id: String): Memory?
}