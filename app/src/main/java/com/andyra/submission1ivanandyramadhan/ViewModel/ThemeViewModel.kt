package com.andyra.submission1ivanandyramadhan.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.andyra.submission1ivanandyramadhan.Data.Local.ThemePreference
import kotlinx.coroutines.launch

class ThemeViewModel (private val pref: ThemePreference) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveTheme(isDarkModeActive)
        }
    }
}