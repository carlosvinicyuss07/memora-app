package com.example.memoraapp.data

import com.example.memoraapp.domain.Memory
import com.example.memoraapp.domain.MemoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.time.LocalDate

class FakeMemoryRepository : MemoryRepository {
    private val mutex = Mutex()

    private val memoryList = mutableListOf<StoredMemory>()
    private val _memories = MutableStateFlow<List<Memory>>(
        listOf(
            Memory(1, "Pôr do Sol na Praia", "teste", LocalDate.now()),
            Memory(2, "Luzes da Cidade", "teste", LocalDate.now())
        )
    )
    override fun getAllMemories(): Flow<List<Memory>> = _memories.asStateFlow()

    override suspend fun insert(memory: Memory) {
        mutex.withLock {
            memoryList.add(
                StoredMemory(
                    id = generateId(),
                    title = memory.title,
                    description = memory.description,
                    dateIso = memory.date.toString(), // ISO yyyy-MM-dd
                    imageUri = memory.imageUri
                )
            )
            notifyChanges()
        }
    }

    override suspend fun update(memory: Memory) {
        mutex.withLock {
            val index = memoryList.indexOfFirst { it.id == memory.id }
            if (index != -1) {
                memoryList[index] = StoredMemory(
                    id = memory.id,
                    title = memory.title,
                    description = memory.description,
                    dateIso = memory.date.toString(),
                    imageUri = memory.imageUri
                )
                notifyChanges()
            }
        }
    }

    override suspend fun delete(memoryId: Int) {
        mutex.withLock {
            memoryList.removeAll { it.id == memoryId }
            notifyChanges()
        }
    }

    override suspend fun getById(id: Int): Memory? {
        return mutex.withLock {
            memoryList.firstOrNull { it.id == id }?.toDomain()
        }
    }

    private fun notifyChanges() {
        _memories.value = memoryList.map { it.toDomain() }
    }

    private fun generateId(): Int {
        return (memoryList.maxOfOrNull { it.id } ?: 0) + 1
    }

    private data class StoredMemory(
        val id: Int,
        val title: String,
        val description: String,
        val dateIso: String,
        val imageUri: String?
    ) {
        fun toDomain(): Memory =
            Memory(
                id = id,
                title = title,
                description = description,
                date = LocalDate.parse(dateIso),
                imageUri = imageUri
            )
    }

}