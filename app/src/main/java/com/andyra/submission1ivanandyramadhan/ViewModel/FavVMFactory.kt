package com.andyra.submission1ivanandyramadhan.ViewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavVMFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: FavVMFactory? = null

        @JvmStatic
        fun getInstance(application: Application): FavVMFactory {
            if (INSTANCE == null) {
                synchronized(FavVMFactory::class.java) {
                    INSTANCE = FavVMFactory(application)
                }
            }
            return INSTANCE as FavVMFactory
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}