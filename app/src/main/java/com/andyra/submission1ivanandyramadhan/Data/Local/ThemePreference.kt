package com.andyra.submission1ivanandyramadhan.Data.Local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.andyra.submission1ivanandyramadhan.R
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

class ThemePreference private constructor(private val mDataStore: DataStore<Preferences>) {
    private val THEME_KEY = booleanPreferencesKey(R.string.theme_key.toString())

    fun getThemeSetting(): Flow<Boolean> {
        return mDataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveTheme(isDarkModeActive: Boolean) {
        mDataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ThemePreference? = null

        fun getInstance(mDataStore: DataStore<Preferences>): ThemePreference {
            return INSTANCE ?: synchronized(this) {
                val mInstance = ThemePreference(mDataStore)
                INSTANCE = mInstance
                mInstance
            }
        }
    }
}