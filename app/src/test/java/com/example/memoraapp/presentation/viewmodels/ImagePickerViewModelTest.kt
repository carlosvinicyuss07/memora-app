package com.example.memoraapp.presentation.viewmodels

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ImagePickerViewModelTest {

    private lateinit var viewModel: ImagePickerViewModel

    @Before
    fun setup() {
        viewModel = ImagePickerViewModel()
    }

    @Test
    fun `estado inicial deve ser nulo`() {
        assertNull(viewModel.selectedImageUri.value)
    }

    @Test
    fun `ao selecionar imagem deve atualizar uri`() {
        // GIVEN
        val uri = "content://image/test"

        // WHEN
        viewModel.setSelectedImage(uri)

        // THEN
        assertEquals(uri, viewModel.selectedImageUri.value)
    }

    @Test
    fun `ao limpar imagem deve voltar para null`() {
        viewModel.setSelectedImage("content://image/test")

        viewModel.clear()

        assertNull(viewModel.selectedImageUri.value)
    }
}