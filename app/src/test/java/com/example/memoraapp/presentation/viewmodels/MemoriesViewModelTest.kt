package com.example.memoraapp.presentation.viewmodels

import com.example.memoraapp.R
import com.example.memoraapp.domain.Memory
import com.example.memoraapp.domain.MemoryRepository
import com.example.memoraapp.presentation.ui.screens.memories.MemoriesScreenEvent
import com.example.memoraapp.presentation.ui.screens.memories.MemoriesScreenSideEffect
import com.example.memoraapp.presentation.ui.util.UiText
import com.example.memoraapp.util.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import kotlin.collections.emptyList

class MemoriesViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var repository: MemoryRepository
    private lateinit var viewModel: MemoriesViewModel

    @Before
    fun setup() {
        repository = mockk()
        viewModel = MemoriesViewModel(repository)
    }

    @Test
    fun `quando entrar na tela deve chamar getAllMemories do repositorio`() = runTest {
        // GIVEN
        val memoriesFlow = flowOf<List<Memory>>(value = emptyList())

        every { repository.getAllMemories() } returns memoriesFlow

        // WHEN
        viewModel.onEvent(MemoriesScreenEvent.OnInit)
        advanceUntilIdle()

        // THEN
        verify(exactly = 1) {
            repository.getAllMemories()
        }
    }

    @Test
    fun `quando entrar na tela deve carregar memorias do repositorio`() = runTest {
        // GIVEN
        val memories = listOf(Memory(id = 1, title = "Teste", description = "", date = LocalDate.now(), imageUri = null))

        every { repository.getAllMemories() } returns flowOf(memories)

        // WHEN
        viewModel.onEvent(MemoriesScreenEvent.OnInit)
        advanceUntilIdle()

        // THEN
        val state = viewModel.uiState.value

        assertFalse(state.isLoading)
        assertEquals(1, state.memories.size)
        assertEquals("Teste", state.memories.first().title)
    }

    @Test
    fun `quando repositorio falhar deve emitir mensagem de erro`() = runTest {
        // GIVEN
        every { repository.getAllMemories() } returns flow { throw RuntimeException("Erro fake") }

        // WHEN
        viewModel.onEvent(MemoriesScreenEvent.OnInit)
        advanceUntilIdle()

        // THEN
        val state = viewModel.uiState.value
        val message = state.erroMessage as UiText.StringResource

        assertFalse(state.isLoading)
        assertEquals(R.string.erro_ao_carregar_memorias, message.resId)
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
        assertEquals(MemoriesScreenSideEffect.NavigateToDetail(1), effect)
    }

}