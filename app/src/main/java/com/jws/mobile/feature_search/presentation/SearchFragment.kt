package com.jws.mobile.feature_search.presentation

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.jws.mobile.R
import com.jws.mobile.core.ui.BaseFragment
import com.jws.mobile.core.utils.ToastHelper
import com.jws.mobile.databinding.FragmentSearchBinding
import com.jws.mobile.feature_search.presentation.epoxy.SearchEpoxyController
import com.jws.mobile.feature_search.presentation.viewmodel.SearchUiEffect
import com.jws.mobile.feature_search.presentation.viewmodel.SearchUiEvent
import com.jws.mobile.feature_search.presentation.viewmodel.SearchUiState
import com.jws.mobile.feature_search.presentation.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private val viewModel: SearchViewModel by viewModels()
    private var controller: SearchEpoxyController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        setOnClickListener()
        setupRecyclerView()
        observeUiState()
    }

    private fun setupRecyclerView() {
        controller = SearchEpoxyController(
            onSearchSelected = { viewModel.onEvent(SearchUiEvent.RequestNavigateToPreview(it)) }
        ).also {
            binding.epoxyRecyclerView.setController(it)
        }
    }

    private fun setupUi() {
        binding.editTextSearch.requestFocus()
        showKeyboard()
    }

    private fun showKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.editTextSearch, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun observeUiState() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch { viewModel.uiState.collect(::processUiState) }
            launch { handleEventFlow() }
        }
    }

    private fun processUiState(state: SearchUiState?) = state?.let {
        controller?.setData(it)
    }

    private suspend fun handleEventFlow() {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is SearchUiEffect.ShowToastError,
                is SearchUiEffect.ShowToastMessage -> showToast(event)

                is SearchUiEffect.NavigateToPreview -> navigateToPreview(event)
                is SearchUiEffect.OnCancelClicked -> cancelClick()
            }
        }
    }

    private fun showToast(effect: SearchUiEffect) {
        val (message, layout) = when (effect) {
            is SearchUiEffect.ShowToastError -> effect.error to R.layout.toast_error
            is SearchUiEffect.ShowToastMessage -> effect.message to R.layout.toast_success
            else -> return
        }
        ToastHelper.message(message, layout, requireContext())
    }

    private fun navigateToPreview(event: SearchUiEffect.NavigateToPreview) {
        val action = SearchFragmentDirections
            .actionSearchFragmentToPreviewFragment(query = event.value)
        findNavController().navigate(action)
    }

    private fun setOnClickListener() {
        binding.btnCancel.setOnClickListener { viewModel.onEvent(SearchUiEvent.OnCancelClicked) }

        binding.editTextSearch.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event?.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN
            ) {
                viewModel.onEvent(SearchUiEvent.RequestNavigateToPreview(binding.editTextSearch.text.toString()))
                true
            } else {
                false
            }
        }
    }

    private fun cancelClick() = parentFragmentManager.popBackStack()

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSearchBinding.inflate(inflater, container, false)

}