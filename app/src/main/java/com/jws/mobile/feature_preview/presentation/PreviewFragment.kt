package com.jws.mobile.feature_preview.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.jws.mobile.R
import com.jws.mobile.core.ui.BaseFragment
import com.jws.mobile.core.utils.ToastHelper
import com.jws.mobile.databinding.FragmentPreviewBinding
import com.jws.mobile.feature_preview.presentation.epoxy.PreviewEpoxyController
import com.jws.mobile.feature_preview.presentation.viewmodel.PreviewUiEffect
import com.jws.mobile.feature_preview.presentation.viewmodel.PreviewUiEvent
import com.jws.mobile.feature_preview.presentation.viewmodel.PreviewUiState
import com.jws.mobile.feature_preview.presentation.viewmodel.PreviewViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PreviewFragment : BaseFragment<FragmentPreviewBinding>() {
    private val viewModel: PreviewViewModel by viewModels()
    private var controller: PreviewEpoxyController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: PreviewFragmentArgs by navArgs()
        val query = args.query
        setupRecyclerView()
        setupClickListeners()
        observeUiState()
        fetchItems(query)

    }

    private fun setupUi(state: PreviewUiState) {
        binding.editTextSearch.setText(state.query.orEmpty())
        binding.btDelete.visibility = if (state.deleteButtonIsVisible) View.VISIBLE else View.GONE
    }

    private fun fetchItems(query: String?) {
        if (viewModel.uiState.value.query == null) {
            viewModel.onEvent(PreviewUiEvent.FetchItems(query))
        }
    }

    private fun openDetailsScreen(productId: String) {
        val action = PreviewFragmentDirections
            .actionPreviewFragmentToDetailFragment(productId = productId)
        findNavController().navigate(action)
    }

    private fun setupRecyclerView() {
        setupAdapter()
        setupLayoutManager()
    }

    private fun setupClickListeners() {
        binding.editTextSearch.setOnClickListener { viewModel.onEvent(PreviewUiEvent.RequestNavigateToSearch) }
        binding.btDelete.setOnClickListener { viewModel.onEvent(PreviewUiEvent.OnDeleteSearchClicked) }
    }

    private fun setupAdapter() {
        controller = PreviewEpoxyController(
            onPreviewSelected = { viewModel.onEvent(PreviewUiEvent.RequestNavigateToDetails(it)) }
        ).also {
            binding.epoxyRecyclerView.setController(it)
        }
    }

    private fun setupLayoutManager() {
        val spanCount = 2
        val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
        binding.epoxyRecyclerView.layoutManager = gridLayoutManager
        binding.epoxyRecyclerView.itemAnimator = DefaultItemAnimator()
    }

    private fun observeUiState() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch { observeState() }
            launch { observeEvents() }
        }
    }

    private suspend fun observeState() {
        viewModel.uiState.collect(::processUiState)
    }

    private suspend fun observeEvents() {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is PreviewUiEffect.ShowToastError,
                is PreviewUiEffect.ShowToastMessage -> showToast(event)
                is PreviewUiEffect.NavigateToSearch -> navigateToSearch()
                is PreviewUiEffect.NavigateToDetails -> openDetailsScreen(productId = event.productId)
                is PreviewUiEffect.OnDeleteSearchClicked -> viewModel.onEvent(PreviewUiEvent.FetchItems(query = ""))
            }
        }
    }

    private fun showToast(effect: PreviewUiEffect) {
        val (message, layout) = when (effect) {
            is PreviewUiEffect.ShowToastError -> effect.error to R.layout.toast_error
            is PreviewUiEffect.ShowToastMessage -> effect.message to R.layout.toast_success
            else -> return
        }
        ToastHelper.message(message, layout, requireContext())
    }

    private fun navigateToSearch() {
        val action = PreviewFragmentDirections
            .actionPreviewFragmentToSearchFragment()
        findNavController().navigate(action)
    }

    private fun processUiState(state: PreviewUiState?) {
        state?.let {
            controller?.setData(it)
            setupUi(state)
        }
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentPreviewBinding.inflate(inflater, container, false)

}