package com.jws.mobile.feature_details

import com.jws.mobile.core.MainDispatcherRule
import com.jws.mobile.core.TestDispatcherProvider
import com.jws.mobile.feature_detail.domain.FetchDetailByIdUseCase
import com.jws.mobile.feature_detail.presentation.viewmodel.DetailViewModel
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
abstract class DetailsBaseViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    val fetchDetailByIdUseCase: FetchDetailByIdUseCase = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()
    val dispatcherProvider = TestDispatcherProvider(testDispatcher)
    lateinit var viewModel: DetailViewModel

    @Before
    fun setUpBase() {
        Dispatchers.setMain(testDispatcher)
        viewModel = DetailViewModel(
            fetchDetailByIdUseCase,
            dispatcherProvider
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}