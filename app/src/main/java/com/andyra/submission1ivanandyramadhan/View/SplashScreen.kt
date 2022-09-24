package com.andyra.submission1ivanandyramadhan.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.andyra.submission1ivanandyramadhan.BuildConfig
import com.andyra.submission1ivanandyramadhan.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var mbinding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mbinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)
        mbinding.tvSVersion.text = StringBuilder("Version ").append(BuildConfig.VERSION_NAME)
    }
}