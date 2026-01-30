package com.example.memoraapp.presentation.viewmodels

import com.example.memoraapp.data.repository.FakeMemoryRepository
import com.example.memoraapp.presentation.ui.screens.memories.MemoriesScreenEvent
import com.example.memoraapp.presentation.ui.screens.memories.MemoriesScreenSideEffect
import com.example.memoraapp.util.MainDispatcherRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MemoriesViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var repository: FakeMemoryRepository
    private lateinit var viewModel: MemoriesViewModel

    @Before
    fun setup() {
        repository = FakeMemoryRepository()
        viewModel = MemoriesViewModel(repository)
    }

    @Test
    fun `quando entrar na tela deve carregar as memorias do repositorio`() = runTest {
        // WHEN
        viewModel.onEvent(MemoriesScreenEvent.OnInit)
        advanceUntilIdle()

        // THEN
        val state = viewModel.uiState.value

        assertFalse(state.isLoading)
    }

    @Test
    fun `quando clicar no FAB deve direcionar para a tela de formulario de cadastro`() = runTest {
        viewModel.onEvent(MemoriesScreenEvent.OnAddMemoryClick)

        val effect = viewModel.effects.first()
        assertTrue(effect is MemoriesScreenSideEffect.NavigateToCreate)
    }

    @Test
    fun `quando clicar no botao de voltar deve direcionar para a tela anterior`() = runTest {
        viewModel.onEvent(MemoriesScreenEvent.OnBackClick)

        val effect = viewModel.effects.first()
        assertTrue(effect is MemoriesScreenSideEffect.NavigateToPreviousScreen)
    }

    @Test
    fun `quando clicar no card de uma memoria deve exibir os detalhes da memoria`() = runTest {
        viewModel.onEvent(MemoriesScreenEvent.OnMemoryClick(1)) // id deve ser passado

        val effect = viewModel.effects.first()
        assertTrue(effect is MemoriesScreenSideEffect.NavigateToDetail)
    }

}