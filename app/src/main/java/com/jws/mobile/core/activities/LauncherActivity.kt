package com.jws.mobile.core.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.jws.mobile.R
import com.jws.mobile.auth.presentation.AuthUiEffect
import com.jws.mobile.auth.presentation.AuthViewModel
import com.jws.mobile.core.utils.ToastHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LauncherActivity : BaseActivity() {
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        setupUiTheme()
        observeViewModel()
    }

    private fun observeViewModel() = lifecycleScope.launch {
        viewModel.eventFlow.collect { effect ->
            when (effect) {
                is AuthUiEffect.NavigateToMain -> {
                    startActivity(Intent(this@LauncherActivity, MainActivity::class.java))
                    finish()
                }
                is AuthUiEffect.ShowToastError -> {
                    ToastHelper.message(effect.error, R.layout.toast_error, applicationContext)
                    setContentView(R.layout.activity_launcher_error)
                }
            }
        }
    }

}


