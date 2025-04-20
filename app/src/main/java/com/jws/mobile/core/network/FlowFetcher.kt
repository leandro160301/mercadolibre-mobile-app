package com.jws.mobile.core.network

import com.jws.mobile.core.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class FlowFetcher @Inject constructor() {
    fun <T> fetchData(
        fetchFlow: suspend () -> Flow<List<T>>,
        viewModelScope: CoroutineScope,
        onError: suspend (String) -> Unit,
    ): StateFlow<List<T>> {
        return flow {
            try {
                fetchFlow().collect { data ->
                    emit(data)
                }
            } catch (e: Exception) {
                onError(e.message ?: "Error al obtener los datos, haga RESET del equipo")
                emit(emptyList())
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun <T> fetchData(
        fetchFlow: suspend () -> Flow<List<T>>,
        viewModelScope: CoroutineScope,
    ): StateFlow<Resource<List<T>>> {
        return flow {
            emit(Resource.Loading())

            try {
                fetchFlow().collect { data ->
                    emit(Resource.Success(data))
                }
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        error = e.message ?: "Error al obtener los datos, haga RESET del equipo"
                    )
                )
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, Resource.Loading())
    }

}