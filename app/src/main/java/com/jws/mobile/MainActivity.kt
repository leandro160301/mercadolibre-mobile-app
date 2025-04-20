package com.jws.mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUiTheme()
    }

    private fun setupUiTheme() {
        try {
            window.statusBarColor = ContextCompat.getColor(this, R.color.amarillo)
            window.navigationBarColor = ContextCompat.getColor(this, R.color.amarillo)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


