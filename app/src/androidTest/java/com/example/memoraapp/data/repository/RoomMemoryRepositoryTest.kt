package com.example.memoraapp.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.memoraapp.data.local.AppDatabase
import com.example.memoraapp.data.local.MemoryDao
import com.example.memoraapp.domain.Memory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate

@RunWith(AndroidJUnit4::class)
class RoomMemoryRepositoryTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: MemoryDao
    private lateinit var repository: RoomMemoryRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        dao = database.memoryDao()
        repository = RoomMemoryRepository(dao)
    }

    @After
    fun closeDb() {
        database.close()
    }

    private fun generateTestMemory(): Memory {
        return Memory(
            id = 0,
            title = "Teste",
            description = "Desc",
            date = LocalDate.of(2026, 1, 1),
            imageUri = "uri_teste"
        )
    }

    @Test
    fun insert_quandoChamado_devePersistirMemoria() = runTest {
        val memory = generateTestMemory()

        repository.insert(memory)

        val inserted = repository.getAllMemories().first().first()
        val expected = memory.copy(id = inserted.id)

        assertEquals(expected, inserted)
    }

    @Test
    fun getAllMemories_quandoChamado_deveRetornarMemoriasInseridas() = runTest {
        val memory = generateTestMemory()

        repository.insert(memory)

        val result = repository.getAllMemories().first()

        assertEquals(1, result.size)
        assertEquals("Teste", result.first().title)
        assertEquals(LocalDate.of(2026, 1, 1), result.first().date)
        assertEquals("uri_teste", result.first().imageUri)
    }

    @Test
    fun getMemoryById_quandoChamado_deveRetornarMemoriaCorreta() = runTest {
        val memory = generateTestMemory()

        repository.insert(memory)

        val inserted = repository.getAllMemories().first().first()
        val result = repository.getMemoryById(inserted.id)
        val expected = memory.copy(id = inserted.id)

        assertNotNull(result)
        assertEquals(expected, result)
    }

    @Test
    fun delete_quandoChamado_deveRemoverMemoriaInserida() = runTest {
        val memory = generateTestMemory()

        repository.insert(memory)

        val inserted = repository.getAllMemories().first().first()

        repository.delete(inserted.id)

        val result = repository.getAllMemories().first()

        assertTrue(result.isEmpty())
    }

    @Test
    fun update_quandoChamado_deveAtualizarMemoriaExistente() = runTest {
        val memory = generateTestMemory()

        repository.insert(memory)

        val inserted = repository.getAllMemories().first().first()

        repository.update(inserted.copy(title = "Atualizada"))

        val updated = repository.getAllMemories().first().first()

        assertEquals("Atualizada", updated.title)
    }

    @Test
    fun getMemoryById_quandoNaoExistir_deveRetornarNull() = runTest {
        val result = repository.getMemoryById(999)

        assertNull(result)
    }

    @Test
    fun getAllMemories_comMultiplosRegistros_deveRetornarTodos() = runTest {
        repository.insert(generateTestMemory())
        repository.insert(generateTestMemory().copy(title = "Teste2"))

        val result = repository.getAllMemories().first()

        assertEquals(2, result.size)
    }
}