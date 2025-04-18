package com.jws.jwsapi.core.files

import com.jws.jwsapi.shared.MainDispatcherRule
import com.jws.jwsapi.shared.TestDispatcherProvider
import com.jws.jwsapi.core.files.domain.FileManager
import com.jws.jwsapi.core.files.domain.usecase.GetFilesUseCase
import com.jws.jwsapi.core.files.domain.usecase.GetStoragesUseCase
import com.jws.jwsapi.core.files.domain.usecase.UpdateSystemUseCase
import com.jws.jwsapi.core.files.presentation.viewmodel.FileViewModel
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class FileBaseViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    protected val getFilesUseCase: GetFilesUseCase = mockk(relaxed = true)
    protected val getStoragesUseCase: GetStoragesUseCase = mockk(relaxed = true)
    protected val fileManager: FileManager = mockk(relaxed = true)
    protected val updateSystemUseCase: UpdateSystemUseCase = mockk(relaxed = true)
    val testDispatcher = StandardTestDispatcher()
    private val dispatcherProvider = TestDispatcherProvider(testDispatcher)
    lateinit var viewModel: FileViewModel

    @Before
    fun setUpBase() {
        Dispatchers.setMain(testDispatcher)
        viewModel = FileViewModel(
            getFilesUseCase,
            getStoragesUseCase,
            fileManager,
            updateSystemUseCase,
            dispatcherProvider
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}