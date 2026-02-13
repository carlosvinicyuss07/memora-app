package com.example.memoraapp.data.mapper

import com.example.memoraapp.data.local.MemoryEntity
import com.example.memoraapp.domain.Memory
import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate

class MemoryMapperTest {

    @Test
    fun toDomain_deveMapearEntityParaDomainCorretamente() {
        val entity = MemoryEntity(
            id = 10,
            title = "Titulo",
            description = "Desc",
            date = "2026-02-09",
            imageUri = "uri_teste"
        )

        val domain = entity.toDomain()

        assertEquals(10, domain.id)
        assertEquals("Titulo", domain.title)
        assertEquals("Desc", domain.description)
        assertEquals(LocalDate.parse("2026-02-09"), domain.date)
        assertEquals("uri_teste", domain.imageUri)
    }

    @Test
    fun toEntity_deveMapearDomainParaEntityCorretamente() {
        val domain = Memory(
            id = 0,
            title = "Titulo",
            description = "Desc",
            date = LocalDate.parse("2026-02-09"),
            imageUri = null
        )

        val entity = domain.toEntity()

        assertEquals(0, entity.id)
        assertEquals("Titulo", entity.title)
        assertEquals("Desc", entity.description)
        assertEquals("2026-02-09", entity.date)
        assertNull(entity.imageUri)
    }

    @Test
    fun mapper_deveManterDadosAoIrEVoltar() {
        val original = Memory(
            id = 5,
            title = "Teste",
            description = "Desc",
            date = LocalDate.now(),
            imageUri = "uri"
        )

        val entity = original.toEntity()
        val domain = entity.toDomain()

        assertEquals(original.copy(id = domain.id), domain)
    }
}