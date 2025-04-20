package com.jws.jwsapi.feature_detail.presentation

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
import com.jws.jwsapi.R
import com.jws.jwsapi.databinding.FragmentDetailBinding
import com.jws.jwsapi.feature_detail.domain.Detail
import com.jws.jwsapi.feature_preview.presentation.viewmodel.PreviewUiEffect
import com.jws.jwsapi.feature_preview.presentation.viewmodel.PreviewUiEvent
import com.jws.jwsapi.shared.BaseFragment
import com.jws.jwsapi.utils.ToastHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    private val viewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: DetailFragmentArgs by navArgs()
        val productId = args.productId
        setOnClickListener()
        observeUiState()
        fetchDetails(productId)
    }

    private fun fetchDetails(productId: String) {
        viewModel.onEvent(DetailUiEvent.FetchDetails(productId))
    }

    private fun navigateToSearch() {
        val action =  DetailFragmentDirections
            .actionDetailFragmentToSearchFragment()
        findNavController().navigate(action)
    }

    private fun setOnClickListener() {
        binding.buttonBack.setOnClickListener { viewModel.onEvent(DetailUiEvent.OnBackClicked) }
        binding.editTextSearch.setOnClickListener { viewModel.onEvent(DetailUiEvent.RequestNavigateToSearch) }
    }


    private fun observeUiState() = viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//            launch { viewModel.uiState.collect(::processUiState) }
            launch { handleEventFlow() }
        }
    }

    private suspend fun handleEventFlow(): Unit = viewModel.eventFlow.collect { event ->
        when(event) {
            is DetailUiEffect.ShowToastError -> ToastHelper.message(event.error, R.layout.toast_error, requireContext())
            is DetailUiEffect.ShowToastMessage ->  ToastHelper.message(event.message, R.layout.toast_success, requireContext())
            is DetailUiEffect.NavigateToSearch -> { navigateToSearch() }
            is DetailUiEffect.OnBackClicked -> { backClick() }
        }
    }

    private fun backClick() = parentFragmentManager.popBackStack()

/*
    private fun processUiState(state: FileUiState?) = state?.let {
        controller?.setData(it)
        FileUiHelper.updateUI(binding, state)
    }
*/


    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentDetailBinding.inflate(inflater, container, false)

}