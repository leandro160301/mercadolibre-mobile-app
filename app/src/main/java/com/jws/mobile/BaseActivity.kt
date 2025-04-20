package com.jws.mobile

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

open class BaseActivity : AppCompatActivity() {
    fun setupUiTheme() {
        try {
            window.statusBarColor = ContextCompat.getColor(this, R.color.amarillo)
            window.navigationBarColor = ContextCompat.getColor(this, R.color.amarillo)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}