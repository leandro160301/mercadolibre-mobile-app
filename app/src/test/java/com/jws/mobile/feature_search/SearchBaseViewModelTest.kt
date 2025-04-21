package com.jws.mobile.feature_search

import com.jws.mobile.core.MainDispatcherRule
import com.jws.mobile.core.TestDispatcherProvider
import com.jws.mobile.feature_detail.domain.FetchDetailByIdUseCase
import com.jws.mobile.feature_detail.presentation.viewmodel.DetailViewModel
import com.jws.mobile.feature_search.domain.AddRecentSearchUseCase
import com.jws.mobile.feature_search.domain.GetRecentSearchUseCase
import com.jws.mobile.feature_search.presentation.viewmodel.SearchViewModel
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
abstract class SearchBaseViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    val getRecentSearchUseCase: GetRecentSearchUseCase = mockk(relaxed = true)
    val addRecentSearchUseCase: AddRecentSearchUseCase = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()
    val dispatcherProvider = TestDispatcherProvider(testDispatcher)
    lateinit var viewModel: SearchViewModel

    @Before
    fun setUpBase() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SearchViewModel(
            getRecentSearchUseCase,
            addRecentSearchUseCase,
            dispatcherProvider
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}