package com.jws.jwsapi.feature_preview.presentation

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jws.jwsapi.core.files.domain.File
import com.jws.jwsapi.core.files.domain.Storage
import com.jws.jwsapi.feature_preview.domain.FetchItemsBySellerUseCase
import com.jws.jwsapi.feature_preview.domain.items.ItemsResponse
import com.jws.jwsapi.shared.DispatcherProvider
import com.jws.jwsapi.shared.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(
    private val fetchItemsBySellerUseCase: FetchItemsBySellerUseCase,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _uiState = MutableStateFlow(PreviewUiState())
    val uiState: StateFlow<PreviewUiState> = _uiState

    private val _eventFlow = MutableSharedFlow<PreviewUiEffect>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        fetchItemsBySellerUseCase
    }

    fun onEvent(event: PreviewUiEvent) {
        when (event) {
            is PreviewUiEvent.FetchItems -> loadItems()
            is PreviewUiEvent.UpdateSystem -> TODO()
        }
    }

    fun loadItems() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            when (val result = fetchItemsBySellerUseCase()) {
                is Resource.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    val items = result.data?.results.orEmpty()
                    _uiState.value = ItemsUiState(items = items)
                }
                is Resource.Error -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    emitEffect(PreviewUiEffect.ShowToastError(result.error ?: "Error desconocido"))
                }

                is Resource.Loading -> {
                    _uiState.value = _uiState.value.copy(isLoading = true)
                }
            }
        }
    }

    private fun fetchFiles(selectedStorage: Storage) =
        viewModelScope.launch(dispatcherProvider.mainImmediate) {
            _uiState.value = _uiState.value.copy(
                selectedStorage = selectedStorage.name, storageAction = selectedStorage
            )
            getFilesUseCase(selectedStorage).collect { resource ->
                ResourceHandler.handle(
                    resource = resource,
                    onSuccess = { files ->
                        _uiState.value = _uiState.value.copy(isLoading = false, filesList = files)
                    },
                    onError = { error ->
                        setupLoading(false)
                        emitEffect(FileUiEffect.ShowToastError(error))
                        goBackToStorageList()
                    },
                    onLoading = { setupLoading(true) }
                )
            }
        }

    private fun goBackToStorageList() {
        _uiState.value = _uiState.value.copy(selectedStorage = null, filesList = emptyList())
        fetchStorages()
    }

    private fun executeFileOperation(
        file: File,
        operation: suspend (File) -> Flow<Resource<String>>,
    ) {
        if (_uiState.value.selectedStorage == null) {
            emitEffect(FileUiEffect.ShowToastError("Error: seleccione un almacenamiento"))
            return
        }

        viewModelScope.launch(dispatcherProvider.io) {
            operation(file).collect { resource -> handleResourceStates(resource) }
            _uiState.value = _uiState.value.copy(actionButtonVisibility = View.INVISIBLE)
        }
    }

    private fun moveFile(file: File) = executeFileOperation(file) {
        fileManager.moveFile(getPath(it), _uiState.value.storageAction?.path + it.name)
    }

    private fun copyFile(file: File) = executeFileOperation(file) {
        fileManager.copyFile(getPath(it), _uiState.value.storageAction?.path + it.name)
    }

    private fun renameFile(file: File, newName: String) = executeFileOperation(file) {
        fileManager.renameFile(getPath(it), file.storage.path + newName + file.extension)
    }

    private fun deleteFile(file: File) =
        executeFileOperation(file) { fileManager.deleteFile(getPath(it)) }

    private fun prepareFile(selectedFile: File, actionType: ActionType) {
        _uiState.value = _uiState.value.copy(
            actionType = actionType,
            fileAction = selectedFile,
            actionButtonText = if (actionType == ActionType.COPY) "COPIAR" else "MOVER",
            actionButtonVisibility = View.VISIBLE
        )
    }

    private fun finishActionFile() = when (_uiState.value.actionType) {
        ActionType.COPY -> _uiState.value.fileAction?.let { copyFile(it) }
        else -> _uiState.value.fileAction?.let { moveFile(it) }
    }

    private fun getPath(file: File) = file.storage.path + file.name

    private fun handleResourceStates(operation: Resource<String>) = ResourceHandler.handle(
        resource = operation,
        onSuccess = { message ->
            emitEffect(FileUiEffect.ShowToastMessage(message))
            _uiState.value.storageAction?.let { fetchFiles(it) }
        },
        onError = { error ->
            setupLoading(false)
            emitEffect(FileUiEffect.ShowToastError(error))
        },
        onLoading = { setupLoading(true) }
    )

    private fun emitEffect(effect: PreviewUiEffect) = viewModelScope.launch(dispatcherProvider.mainImmediate) {
        _eventFlow.emit(effect)
    }

    private fun setupLoading(value: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = value)
    }

    enum class ActionType {
        MOVE, COPY
    }

}