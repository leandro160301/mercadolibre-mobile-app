package com.jws.mobile.core.ui

import android.content.Context
import androidx.fragment.app.Fragment
import com.jws.mobile.R
import com.jws.mobile.core.utils.ToastHelper
import com.jws.mobile.feature_detail.presentation.viewmodel.DetailUiEffect
import com.jws.mobile.feature_preview.presentation.viewmodel.PreviewUiEffect
import com.jws.mobile.feature_search.presentation.viewmodel.SearchUiEffect

object ToastEffect {
    fun <T> showToast(effect: T, context: Context) {
        val (message, layout) = when (effect) {
            is DetailUiEffect.ShowToastError -> effect.error to R.layout.toast_error
            is DetailUiEffect.ShowToastMessage -> effect.message to R.layout.toast_success
            is PreviewUiEffect.ShowToastError -> effect.error to R.layout.toast_error
            is PreviewUiEffect.ShowToastMessage -> effect.message to R.layout.toast_success
            is SearchUiEffect.ShowToastError -> effect.error to R.layout.toast_error
            is SearchUiEffect.ShowToastMessage -> effect.message to R.layout.toast_success
            else -> return
        }
        ToastHelper.message(message, layout, context)
    }
}