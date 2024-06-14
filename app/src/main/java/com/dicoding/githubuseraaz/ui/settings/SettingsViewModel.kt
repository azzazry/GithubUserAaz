package com.dicoding.githubuseraaz.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.githubuseraaz.utils.SettingPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(private val pref: SettingPreferences) : ViewModel() {

    private val _themeSetting = MutableStateFlow(false)
    val themeSetting: StateFlow<Boolean> get() = _themeSetting

    init {
        viewModelScope.launch {
            pref.getThemeSetting().collect { theme ->
                _themeSetting.value = theme
            }
        }
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}
