package com.example.memoraapp.data.repository

import com.example.memoraapp.domain.Memory
import com.example.memoraapp.domain.MemoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class FakeMemoryRepository(
    initialMemories: List<StoredMemory> = defaultMemories(),
    private val failure: RepositoryFailure? = null
) : MemoryRepository {
    private val memoryList = initialMemories.toMutableList()

    companion object {
        private fun defaultMemories() = listOf(
            StoredMemory(1, "Pôr do Sol na Praia", "teste", LocalDate.now().toString(), null),
            StoredMemory(2, "Luzes da Cidade", "teste", LocalDate.now().toString(), null)
        )
    }

    private val _memories = MutableStateFlow(
        memoryList.map { it.toDomain() }
    )

    override fun getAllMemories(): Flow<List<Memory>> = _memories.asStateFlow()

    override suspend fun insert(memory: Memory) {
        failIf(RepositoryFailure.Insert)
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

    override suspend fun update(memory: Memory) {
        failIf(RepositoryFailure.Update)
        val index = memoryList.indexOfFirst { it.id == memory.id }
        if (index != -1) {
            memoryList[index] = memoryList[index].copy(
                title = memory.title,
                description = memory.description,
                dateIso = memory.date.toString(),
                imageUri = memory.imageUri
            )
            notifyChanges()
        }
    }

    override suspend fun delete(memoryId: Int) {
        failIf(RepositoryFailure.Delete)
        memoryList.removeAll { it.id == memoryId }
        notifyChanges()
    }

    override suspend fun getMemoryById(id: Int): Memory? {
        failIf(RepositoryFailure.GetById)
        return memoryList.firstOrNull { it.id == id }?.toDomain()
    }

    private fun notifyChanges() {
        _memories.value = memoryList.map(StoredMemory::toDomain)
    }

    private fun generateId(): Int {
        return (memoryList.maxOfOrNull { it.id } ?: 0) + 1
    }

    private fun failIf(expected: RepositoryFailure) {
        if (failure == expected) {
            throw FakeRepositoryException(expected)
        }
    }

    data class StoredMemory(
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