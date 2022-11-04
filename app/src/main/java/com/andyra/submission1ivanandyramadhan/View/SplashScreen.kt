package com.andyra.submission1ivanandyramadhan.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.andyra.submission1ivanandyramadhan.BuildConfig
import com.andyra.submission1ivanandyramadhan.R
import com.andyra.submission1ivanandyramadhan.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var mBinding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, delayTime)
        mBinding.tvSVersion.text = StringBuilder(getString(R.string.tVersion)).append(BuildConfig.VERSION_NAME)
    }

    companion object {
        private val delayTime:Long = 1500
    }
}