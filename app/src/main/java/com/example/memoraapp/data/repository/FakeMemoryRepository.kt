package com.example.memoraapp.data.repository

import com.example.memoraapp.data.Memory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeMemoryRepository : MemoryRepository {

    private val memoryList = mutableListOf<Memory>()

    private val _memories = MutableStateFlow<List<Memory>>(emptyList())
    override fun getAllMemories(): Flow<List<Memory>> = _memories

    override suspend fun insert(memory: Memory) {
        memoryList.add(memory.copy(id = generateId()))
        notifyChanges()
    }

    override suspend fun update(memory: Memory) {
        val index = memoryList.indexOfFirst { it.id == memory.id }
        if (index != -1) {
            memoryList[index] = memory
            notifyChanges()
        }
    }

    override suspend fun delete(memory: Memory) {
        memoryList.removeAll { it.id == memory.id }
        notifyChanges()
    }

    override suspend fun getById(id: Int): Memory? {
        return memoryList.firstOrNull { it.id == id }
    }

    private fun notifyChanges() {
        _memories.value = memoryList.toList()
    }

    private fun generateId(): Int {
        return (memoryList.maxOfOrNull { it.id } ?: 0) + 1
    }

}
