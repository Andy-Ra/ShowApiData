package com.andyra.submission1ivanandyramadhan.ViewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FavVMFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: FavVMFactory? = null

        @JvmStatic
        fun getInstance(mApplication: Application): FavVMFactory {
            if (INSTANCE == null) {
                synchronized(FavVMFactory::class.java) {
                    INSTANCE = FavVMFactory(mApplication)
                }
            }
            return INSTANCE as FavVMFactory
        }
    }

    override fun <T : ViewModel> create(mModelClass: Class<T>): T {
        if (mModelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(mApplication) as T
        }
        throw IllegalArgumentException(StringBuilder("Unknown ViewModel class: ").append(mModelClass.name).toString())
    }
}