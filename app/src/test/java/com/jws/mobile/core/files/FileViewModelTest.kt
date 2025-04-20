package com.jws.mobile.core.files

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@ExperimentalCoroutinesApi
class FileViewModelTest: FileBaseViewModelTest() {

    @Test
    fun `cuando fetchFiles es exitoso, uiState debe tener archivos para mostrar`() = runTest {
 /*       val archivos = listOf(fileFactory())
        coEvery { getFilesUseCase(any()) } returns flowOf(Resource.Success(archivos)) //simulamos usecase con valor

        viewModel.onEvent(FileUiEvent.FetchFiles(Storage("SD")))
        advanceUntilIdle() // verificamos que finalizaron las operaciones asincronicas

        assertEquals(archivos, viewModel.uiState.value.filesList)*/
    }

    @Test
    fun `cuando fetchStorage es exitoso, uiState debe tener almacenamientos para mostrar`() = runTest {
/*        val storages = listOf(Storage(name = "", path = "", icon = null))
        coEvery { getStoragesUseCase() } returns flowOf(Resource.Success(storages))

        viewModel.onEvent(FileUiEvent.GoBackToStorageList)

        advanceUntilIdle()
        assertEquals(storages, viewModel.uiState.value.storageList)*/
    }

    @Test
    fun `cuando fetchFiles falla, debe mostrar error y volver a la lista de almacenamientos`() = runTest {
/*        coEvery { getFilesUseCase(any()) } returns flowOf(Resource.Error(error = "Error al obtener archivos"))

        viewModel.onEvent(FileUiEvent.FetchFiles(Storage("SD")))
        advanceUntilIdle()

        assertEquals(null, viewModel.uiState.value.selectedStorage)
        assertEquals(emptyList<File>(), viewModel.uiState.value.filesList)*/
    }

    @Test
    fun `cuando updateSystem debe emitir notificacion`() = runTest {
/*        viewModel.onEvent(FileUiEvent.UpdateSystem(fileFactory()))

        val event = viewModel.eventFlow.first()
        assertTrue(event is FileUiEffect.ShowNotificationUpdateSystem)*/
    }

    @Test
    fun `cuando prepareFile con COPY debe cambiar visibilidad y texto boton`() = runTest {
     /*   val file = fileFactory()

        viewModel.onEvent(FileUiEvent.PrepareFile(file, FileViewModel.ActionType.COPY))

        assertEquals(file, viewModel.uiState.value.fileAction)
        assertEquals("COPIAR", viewModel.uiState.value.actionButtonText)
        assertEquals(View.VISIBLE, viewModel.uiState.value.actionButtonVisibility)*/
    }

    @Test
    fun `cuando deleteFile falla, debe emitir error`() = runTest {
/*        val eventosEmitidos = mutableListOf<FileUiEffect>()
        val job = launch { viewModel.eventFlow.collect { eventosEmitidos.add(it) } } //escuchamos eventos
        viewModel.onEvent(FileUiEvent.FetchFiles(Storage("SD")))
        advanceUntilIdle()
        coEvery { fileManager.deleteFile(any()) } returns flowOf(Resource.Error(error = ""))
        viewModel.onEvent(FileUiEvent.DeleteFile(fileFactory()))
        advanceUntilIdle()
        assertTrue(eventosEmitidos.any { it is FileUiEffect.ShowToastError })
        job.cancel()*/
    }

/*    private fun fileFactory() =
        File(
            name = "prueba.txt",
            date = "08/04",
            size = "2mb",
            extension = ".txt",
            storage = Storage()
        )*/
}