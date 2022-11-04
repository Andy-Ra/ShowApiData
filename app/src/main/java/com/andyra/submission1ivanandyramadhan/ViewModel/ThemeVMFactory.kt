package com.andyra.submission1ivanandyramadhan.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andyra.submission1ivanandyramadhan.Data.Local.ThemePreference

class ThemeVMFactory (private val mPref: ThemePreference) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(mModelClass: Class<T>): T {
        if (mModelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(mPref) as T
        }
        throw IllegalArgumentException(StringBuilder("Unknown ViewModel class: ").append(mModelClass.name).toString())
    }
}