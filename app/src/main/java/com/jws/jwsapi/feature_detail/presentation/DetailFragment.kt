package com.jws.jwsapi.feature_detail.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jws.jwsapi.databinding.FragmentSearchBinding
import com.jws.jwsapi.shared.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentSearchBinding>() {
/*    private val viewModel: FileViewModel by viewModels()
    private var controller: FileEpoxyController? = null*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
   /*     setupUI()
        setupRecyclerView()
        setOnClickListener()
        observeUiState()*/
    }
/*
    private fun setupRecyclerView() {
        setupAdapter()
        setupEpoxyView()
    }

    private fun setOnClickListener() {
        binding.headerContainer.setOnClickListener { viewModel.onEvent(FileUiEvent.GoBackToStorageList) }
        binding.btTransfer.setOnClickListener { viewModel.onEvent(FileUiEvent.FinishActionFile) }
    }

    private fun setupAdapter() {
        controller = FileEpoxyController(
            onFileSelected = { FileUiHelper.showFileDialog(it, requireContext(), viewModel) },
            onStorageClick = {
                lifecycleScope.launch { viewModel.onEvent(FileUiEvent.FetchFiles(it)) }
            }).also {
            binding.epoxyRecyclerView.setController(it)
        }
    }

    private fun setupEpoxyView() {
        val spanCount = 4
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
        getMainView()?.let { FileUiHelper.handleEvent(event, requireContext(), it) }
    }

    private fun processUiState(state: FileUiState?) = state?.let {
        controller?.setData(it)
        FileUiHelper.updateUI(binding, state)
    }

    private fun setupUI() = FileUiHelper.applyFadeAnimation(binding.lnData)*/

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentSearchBinding.inflate(inflater, container, false)

}