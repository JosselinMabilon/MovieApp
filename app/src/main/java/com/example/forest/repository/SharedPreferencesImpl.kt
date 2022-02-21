package com.example.forest.repository

import android.content.SharedPreferences

class SharedPreferencesImpl(
    private val sharedPreferences: SharedPreferences
) : ISharedPreferences {

    override fun putBooleanValue(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    override fun getBooleanValue(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }
}