package com.example.memoraapp.data.repository

import com.example.memoraapp.domain.Memory
import com.example.memoraapp.domain.MemoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeMemoryRepositoryWithError : MemoryRepository {
    override fun getAllMemories(): Flow<List<Memory>> =
        flowOf(emptyList())

    override suspend fun insert(memory: Memory) {
        throw RuntimeException("Erro ao salvar memória")
    }

    override suspend fun update(memory: Memory) {
        throw RuntimeException("Erro ao atualizar memória")
    }

    override suspend fun delete(memoryId: Int) {
        TODO("Será implementado futuramente")
    }

    override suspend fun getMemoryById(id: Int): Memory? {
        return null
    }

}