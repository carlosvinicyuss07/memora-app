package com.example.memoraapp.data.repository

import com.example.memoraapp.data.local.MemoryDao
import com.example.memoraapp.data.mapper.toDomain
import com.example.memoraapp.data.mapper.toEntity
import com.example.memoraapp.domain.Memory
import com.example.memoraapp.domain.MemoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomMemoryRepository(
    private val dao: MemoryDao
) : MemoryRepository {

    override fun getAllMemories(): Flow<List<Memory>> =
        dao.getAll().map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun insert(memory: Memory) {
        dao.insert(memory.toEntity())
    }

    override suspend fun update(memory: Memory) {
        dao.update(memory.toEntity())
    }

    override suspend fun delete(memoryId: Int) {
        dao.deleteById(memoryId)
    }

    override suspend fun getMemoryById(id: Int): Memory? =
        dao.getById(id)?.toDomain()

}