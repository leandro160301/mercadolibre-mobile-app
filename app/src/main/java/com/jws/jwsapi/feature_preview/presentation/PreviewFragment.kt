package com.jws.jwsapi.feature_preview.presentation

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
import com.jws.jwsapi.R
import com.jws.jwsapi.databinding.FragmentPreviewBinding
import com.jws.jwsapi.feature_preview.presentation.epoxy.PreviewEpoxyController
import com.jws.jwsapi.feature_preview.presentation.viewmodel.PreviewUiEffect
import com.jws.jwsapi.feature_preview.presentation.viewmodel.PreviewUiEvent
import com.jws.jwsapi.feature_preview.presentation.viewmodel.PreviewUiState
import com.jws.jwsapi.feature_preview.presentation.viewmodel.PreviewViewModel
import com.jws.jwsapi.shared.BaseFragment
import com.jws.jwsapi.utils.ToastHelper
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
        setOnClickListener()
        observeUiState()
        fetchItems(query)
    }

    private fun setupUi(state: PreviewUiState) {
        binding.editTextSearch.setText(if (state.query.isNullOrEmpty()) "" else state.query)
        binding.btDelete.visibility = if (state.deleteButtonIsVisible) View.VISIBLE else View.GONE
    }

    private fun fetchItems(query: String?) {
        viewModel.onEvent(PreviewUiEvent.FetchItems(query))
    }

    private fun openDetailsScreen(productId: String) {
        val action = PreviewFragmentDirections
            .actionPreviewFragmentToDetailFragment(productId = productId)
        findNavController().navigate(action)
    }

    private fun setupRecyclerView() {
        setupAdapter()
        setupEpoxyView()
    }

    private fun setOnClickListener() {
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

    private fun setupEpoxyView() {
        val spanCount = 2
        val gridLayoutManager = GridLayoutManager(requireContext(), spanCount)
        binding.epoxyRecyclerView.layoutManager = gridLayoutManager
        binding.epoxyRecyclerView.itemAnimator = DefaultItemAnimator()
    }

    private fun observeUiState() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch { viewModel.uiState.collect(::processUiState) }
            launch { handleEventFlow() }
        }
    }

    private suspend fun handleEventFlow(): Unit = viewModel.eventFlow.collect { event ->
        when(event) {
            is PreviewUiEffect.ShowToastError -> ToastHelper.message(event.error, R.layout.toast_error, requireContext())
            is PreviewUiEffect.ShowToastMessage ->  ToastHelper.message(event.message, R.layout.toast_success, requireContext())
            is PreviewUiEffect.NavigateToSearch -> navigateToSearch()
            is PreviewUiEffect.NavigateToDetails -> openDetailsScreen(productId = event.productId)
            PreviewUiEffect.OnDeleteSearchClicked -> viewModel.onEvent(PreviewUiEvent.FetchItems())
        }
    }

    private fun navigateToSearch() {
        val action = PreviewFragmentDirections
            .actionPreviewFragmentToSearchFragment()
        findNavController().navigate(action)
    }

    private fun processUiState(state: PreviewUiState?) {
        state?.let  {
            controller?.setData(it)
            setupUi(state)
        }
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentPreviewBinding.inflate(inflater, container, false)

}