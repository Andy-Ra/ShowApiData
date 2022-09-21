package com.andyra.submission1ivanandyramadhan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.viewbinding.BuildConfig
import com.andyra.submission1ivanandyramadhan.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var mbinding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        mbinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(mbinding.root)

        Handler().postDelayed({
            val intent = Intent(this, DetailUser::class.java)
            startActivity(intent)
            finish()
        }, 1500)
        mbinding.tvSVersion.text = StringBuilder("Version ").append(BuildConfig.BUILD_TYPE)
    }
}