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
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.jws.jwsapi.R
import com.jws.jwsapi.databinding.FragmentPreviewBinding
import com.jws.jwsapi.databinding.FragmentSearchBinding
import com.jws.jwsapi.feature_preview.presentation.epoxy.PreviewEpoxyController
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
        setupRecyclerView()
        setOnClickListener()
        observeUiState()
    }

    fun openDetailsScreen() {
        val action = PreviewFragmentDirections
            .actionPreviewFragmentToDetailFragment(productId = "MLA123456")
        findNavController().navigate(action)
    }

    private fun setupRecyclerView() {
        setupAdapter()
        setupEpoxyView()
    }

    private fun setOnClickListener() {
/*        binding.headerContainer.setOnClickListener { viewModel.onEvent(FileUiEvent.GoBackToStorageList) }
        binding.btTransfer.setOnClickListener { viewModel.onEvent(FileUiEvent.FinishActionFile) }*/
    }

    private fun setupAdapter() {
        controller = PreviewEpoxyController(
            onPreviewSelected = { /*FileUiHelper.showFileDialog(it, requireContext(), viewModel)*/ }
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

    private suspend fun handleEventFlow(): Nothing = viewModel.eventFlow.collect { event ->
        when(event) {
            is PreviewUiEffect.ShowToastError -> ToastHelper.message(event.error, R.layout.item_customtoasterror, requireContext())
            is PreviewUiEffect.ShowToastMessage -> {
                /*TODO()*/
            }
        }
    }

    private fun processUiState(state: PreviewUiState?) = state?.let {
        controller?.setData(it)
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentPreviewBinding.inflate(inflater, container, false)

}