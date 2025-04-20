package com.jws.mobile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        setupUiTheme()
        Handler().postDelayed({
            val i = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }, 3000)
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


