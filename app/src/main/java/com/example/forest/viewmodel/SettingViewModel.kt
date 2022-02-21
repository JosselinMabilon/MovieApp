package com.example.forest.viewmodel

import androidx.lifecycle.ViewModel
import com.example.forest.repository.ISharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val sharedPreferences: ISharedPreferences
) : ViewModel() {

    fun loadDarkModeSwitchData(): Boolean {
        return sharedPreferences.getBooleanValue(SWITCH_DM_BOOLEAN_KEY, false)
    }

    fun saveDarkModeSwitchData(key: Boolean) {
        sharedPreferences.putBooleanValue(SWITCH_DM_BOOLEAN_KEY, key)
    }

    companion object {
        private val SWITCH_DM_BOOLEAN_KEY = "SWITCH_DM_BOOLEAN_KEY"
    }
}