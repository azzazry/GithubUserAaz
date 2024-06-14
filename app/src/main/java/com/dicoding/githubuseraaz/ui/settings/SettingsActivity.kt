package com.dicoding.githubuseraaz.ui.settings

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dicoding.githubuseraaz.R
import com.dicoding.githubuseraaz.databinding.ActivitySettingsBinding
import com.dicoding.githubuseraaz.utils.SettingPreferences
import com.dicoding.githubuseraaz.utils.dataStore
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    lateinit var settingsBinding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(settingsBinding.root)

        // Updated version
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)
        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingViewModel = ViewModelProvider(this, SettingsViewModelFactory(pref)).get(SettingsViewModel::class.java)

        switchTheme.isChecked = settingViewModel.themeSetting.value

        @Suppress("DEPRECATION")
        lifecycleScope.launchWhenStarted {
            settingViewModel.themeSetting.collect { isDarkModeActive ->
                switchTheme.isChecked = isDarkModeActive
            }
        }

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            settingViewModel.saveThemeSetting(isChecked)
        }
    }
}