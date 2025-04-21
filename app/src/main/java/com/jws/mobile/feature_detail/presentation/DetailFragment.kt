package com.jws.mobile.feature_detail.presentation

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
import com.jws.mobile.core.ui.BaseFragment
import com.jws.mobile.core.ui.ToastEffect.showToast
import com.jws.mobile.databinding.FragmentDetailBinding
import com.jws.mobile.feature_detail.presentation.epoxy.DetailEpoxyController
import com.jws.mobile.feature_detail.presentation.viewmodel.DetailUiEffect
import com.jws.mobile.feature_detail.presentation.viewmodel.DetailUiEvent
import com.jws.mobile.feature_detail.presentation.viewmodel.DetailUiState
import com.jws.mobile.feature_detail.presentation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    private val viewModel: DetailViewModel by viewModels()
    private var controller: DetailEpoxyController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: DetailFragmentArgs by navArgs()
        val productId = args.productId
        setupAdapter()
        setOnClickListener()
        observeUiState()
        fetchDetails(productId)
    }

    private fun setupAdapter() {
        controller = DetailEpoxyController(
            onImagePageChanged = { viewModel.onEvent(DetailUiEvent.OnImagePageChanged(it)) }
        ).also { binding.epoxyRecyclerView.setController(it) }
    }

    private fun fetchDetails(productId: String) {
        viewModel.onEvent(DetailUiEvent.FetchDetails(productId))
    }

    private fun navigateToSearch() {
        val action = DetailFragmentDirections
            .actionDetailFragmentToSearchFragment()
        findNavController().navigate(action)
    }

    private fun setOnClickListener() {
        binding.buttonBack.setOnClickListener { viewModel.onEvent(DetailUiEvent.OnBackClicked) }
        binding.editTextSearch.setOnClickListener { viewModel.onEvent(DetailUiEvent.RequestNavigateToSearch) }
    }

    private fun observeUiState() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            launch { viewModel.uiState.collect(::processUiState) }
            launch { handleEventFlow() }
        }
    }

    private suspend fun handleEventFlow(): Unit = viewModel.eventFlow.collect { event ->
        when (event) {
            is DetailUiEffect.ShowToastError,
            is DetailUiEffect.ShowToastMessage -> showToast(event, requireContext())

            is DetailUiEffect.NavigateToSearch -> navigateToSearch()
            is DetailUiEffect.OnBackClicked -> backClick()
        }
    }

    private fun backClick() = parentFragmentManager.popBackStack()

    private fun processUiState(state: DetailUiState?) {
        state?.let { controller?.setData(it) }
    }

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentDetailBinding.inflate(inflater, container, false)

}