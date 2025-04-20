package com.jws.mobile.feature_preview

import com.jws.mobile.feature_preview.domain.FetchItemsBySellerUseCase
import com.jws.mobile.feature_preview.domain.FetchPreviewsByIdUseCase
import com.jws.mobile.feature_preview.presentation.viewmodel.PreviewViewModel
import com.jws.mobile.core.MainDispatcherRule
import com.jws.mobile.core.TestDispatcherProvider
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
abstract class PreviewBaseViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    val fetchItemsBySellerUseCase: FetchItemsBySellerUseCase = mockk(relaxed = true)
    val fetchPreviewsByIdUseCase: FetchPreviewsByIdUseCase = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()
    val dispatcherProvider = TestDispatcherProvider(testDispatcher)
    lateinit var viewModel: PreviewViewModel

    @Before
    fun setUpBase() {
        Dispatchers.setMain(testDispatcher)
        viewModel = PreviewViewModel(
            fetchItemsBySellerUseCase,
            fetchPreviewsByIdUseCase,
            dispatcherProvider
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}