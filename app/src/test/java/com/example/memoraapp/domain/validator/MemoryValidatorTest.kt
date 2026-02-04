package com.example.memoraapp.domain.validator

import junit.framework.TestCase
import org.junit.Test

class MemoryValidatorTest {

    private val validator = MemoryValidator()

    @Test
    fun `titulo vazio deve ser invalido`() {
        val result = validator.isValidTitle("")

        TestCase.assertFalse(result)
    }

    @Test
    fun `titulo valido deve retornar true`() {
        val result = validator.isValidTitle("Minha memória")

        TestCase.assertTrue(result)
    }
}