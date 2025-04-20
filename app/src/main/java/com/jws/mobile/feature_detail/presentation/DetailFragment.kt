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
import com.jws.mobile.R
import com.jws.mobile.databinding.FragmentDetailBinding
import com.jws.mobile.core.ui.BaseFragment
import com.jws.mobile.core.utils.ImagePagerAdapter
import com.jws.mobile.core.utils.ToastHelper
import com.jws.mobile.feature_detail.presentation.viewmodel.DetailUiEffect
import com.jws.mobile.feature_detail.presentation.viewmodel.DetailUiEvent
import com.jws.mobile.feature_detail.presentation.viewmodel.DetailUiState
import com.jws.mobile.feature_detail.presentation.viewmodel.DetailViewModel
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
            is DetailUiEffect.ShowToastError -> ToastHelper.message(
                event.error,
                R.layout.toast_error,
                requireContext()
            )

            is DetailUiEffect.ShowToastMessage -> ToastHelper.message(
                event.message,
                R.layout.toast_success,
                requireContext()
            )

            is DetailUiEffect.NavigateToSearch -> {
                navigateToSearch()
            }

            is DetailUiEffect.OnBackClicked -> {
                backClick()
            }
        }
    }

    private fun backClick() = parentFragmentManager.popBackStack()

    private fun processUiState(state: DetailUiState?) = state?.let {
        val detail = it.previewList

        binding.productTitle.text = detail?.title
        binding.productPrice.text = "$${detail?.price}"

        detail?.pictures?.let { pictures ->
            val adapter = ImagePagerAdapter(pictures)
            binding.imagePager.adapter = adapter
            binding.imageIndicator.setViewPager(binding.imagePager)
        }

        // Condición y disponibilidad
        val conditionFormatted = when (detail?.condition) {
            "new" -> "Nuevo"
            "used" -> "Usado"
            else -> "Condición desconocida"
        }

        binding.productWarranty.text = detail?.warranty

        binding.productCondition.text =
            "$conditionFormatted | ${detail?.availableQuantity} disponibles"

        // Info de envío (esto puede que venga de otro campo, si no lo tenés podés dejarlo hardcodeado)
        binding.shippingInfo.text = "Envío gratis a todo el país"

        // Descripción (si tenés campo para esto)
        binding.productDescription.text =
            "Este producto es ideal para..." // Aquí deberías mapear un campo real si lo tenés

        // Detalles técnicos (si tenés campos para mostrar como specs)
        binding.productSpecs.text = buildString {
            append("• Marca: X\n")
            append("• Modelo: Y\n")
            append("• Color: Negro\n")
            append("• Peso: 1.5kg")
        }

        // Botón de compra
        binding.buyButton.setOnClickListener {
            // Acción de compra
        }
    }


    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentDetailBinding.inflate(inflater, container, false)

}