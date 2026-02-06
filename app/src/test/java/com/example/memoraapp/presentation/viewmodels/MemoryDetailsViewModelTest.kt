package com.example.memoraapp.presentation.viewmodels

import com.example.memoraapp.R
import com.example.memoraapp.domain.Memory
import com.example.memoraapp.domain.MemoryRepository
import com.example.memoraapp.presentation.ui.screens.details.MemoryDetailsScreenEvent
import com.example.memoraapp.presentation.ui.screens.details.MemoryDetailsSideEffect
import com.example.memoraapp.presentation.ui.util.UiText
import com.example.memoraapp.util.MainDispatcherRule
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

class MemoryDetailsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private lateinit var repository: MemoryRepository
    private lateinit var viewModel: MemoryDetailsViewModel

    private fun generateTestMemory(userId: Int): Memory {
        return Memory(
            id = userId,
            title = "Teste",
            description = "Desc",
            date = LocalDate.of(2026, 1, 1),
            imageUri = "uri_teste"
        )
    }

    @Before
    fun setup() {
        repository = mockk()

        viewModel = MemoryDetailsViewModel(repository)
    }

    @Test
    fun `ao entrar na tela deve buscar memoria pelo id informado`() = runTest {
        val userId = 1
        val memory = generateTestMemory(userId)

        coEvery { repository.getMemoryById(userId) } returns memory

        viewModel.onEvent(MemoryDetailsScreenEvent.OnInit(userId))
        advanceUntilIdle()

        coVerify(exactly = 1) { repository.getMemoryById(userId) }
    }

    @Test
    fun `ao buscar memoria pelo id informado deve atualizar o uiState com os dados da memoria`() = runTest {
        val userId = 1
        val memory = generateTestMemory(userId)
        val date = LocalDate.of(2026, 1, 1)

        coEvery { repository.getMemoryById(userId) } returns memory

        viewModel.onEvent(MemoryDetailsScreenEvent.OnInit(userId))
        advanceUntilIdle()

        val state = viewModel.uiState.value

        val memoryFromState = Memory(
            id = state.id!!,
            title = state.title,
            description = state.description,
            date = date, // uiState usa String enquanto Memory usa LocalDate para armazenar data
            imageUri = state.imageUri
        )

        assertFalse(state.isLoading)
        assertEquals(memoryFromState, repository.getMemoryById(userId))
    }

    @Test
    fun `em caso de falha ao buscar memoria pelo id informado deve emitir mensagem para o usuario`() = runTest {
        val userId = 1

        coEvery { repository.getMemoryById(userId) } throws RuntimeException("Erro fake")

        viewModel.onEvent(MemoryDetailsScreenEvent.OnInit(userId))
        advanceUntilIdle()

        val state = viewModel.uiState.value
        val effect = viewModel.effects.first()
        val errorEffect = effect as? MemoryDetailsSideEffect.ShowError ?: error("Esperado ShowError, mas foi ${effect::class.simpleName}")
        val message = errorEffect.message as UiText.StringResource

        assertFalse(state.isLoading)
        assertEquals(R.string.erro_ao_carregar_memoria, message.resId)
    }

    @Test
    fun `ao voltar deve fechar tela atual e retornar para a tela anterior`() = runTest {
        // WHEN
        viewModel.onEvent(MemoryDetailsScreenEvent.OnBackClick)

        // THEN
        val effect = viewModel.effects.first()
        assertTrue(effect is MemoryDetailsSideEffect.CloseScreen)
    }

    @Test
    fun `ao clicar no botao editar deve direcionar para o formulario no modo de edicao`() = runTest {
        val userId = 1

        viewModel.onEvent(MemoryDetailsScreenEvent.OnEditClick(userId)) // o id deve ser passado

        val effect = viewModel.effects.first()
        assertEquals(MemoryDetailsSideEffect.NavigateToEdit(userId), effect)
    }

    @Test
    fun `ao clicar no botao excluir deve chamar o metodo deletar memoria`() = runTest {
        val userId = 1

        coEvery { repository.delete(any()) } just Runs

        viewModel.onEvent(MemoryDetailsScreenEvent.OnDeleteClick(userId))
        advanceUntilIdle()

        coVerify (exactly = 1) { repository.delete(userId) }
    }

    @Test
    fun `ao excluir uma memoria deve emitir mensagem de sucesso`() = runTest {
        val userId = 1

        coEvery { repository.delete(any()) } just Runs

        viewModel.onEvent(MemoryDetailsScreenEvent.OnDeleteClick(userId))
        advanceUntilIdle()

        val effect = viewModel.effects.first() as MemoryDetailsSideEffect.ShowSuccessMessage
        val message = effect.message as UiText.StringResource

        assertEquals(R.string.excluido_com_sucesso, message.resId)
    }

    @Test
    fun `ao excluir uma memoria com sucesso deve fechar a tela de detalhes`() = runTest {
        val userId = 1

        coEvery { repository.delete(any()) } just Runs

        viewModel.onEvent(MemoryDetailsScreenEvent.OnDeleteClick(userId))
        advanceUntilIdle()

        val effect = viewModel.effects.first { effect -> effect is MemoryDetailsSideEffect.CloseScreen}

        assertEquals(MemoryDetailsSideEffect.CloseScreen, effect)
    }

    @Test
    fun `em caso de falha ao tentar excluir uma memoria deve emitir mensagem para o usuario`() = runTest {
        val userId = 1

        coEvery { repository.delete(any()) } throws RuntimeException("Erro fake")

        viewModel.onEvent(MemoryDetailsScreenEvent.OnDeleteClick(userId))
        advanceUntilIdle()

        val effect = viewModel.effects.first()
        val errorEffect = effect as? MemoryDetailsSideEffect.ShowError ?: error("Esperado ShowError, mas foi ${effect::class.simpleName}")
        val message = errorEffect.message as UiText.StringResource

        assertEquals(R.string.erro_ao_tentar_excluir, message.resId)
    }
}