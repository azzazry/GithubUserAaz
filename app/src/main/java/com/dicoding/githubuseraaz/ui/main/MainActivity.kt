package com.dicoding.githubuseraaz.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuseraaz.R
import com.dicoding.githubuseraaz.adapter.UserAdapter
import com.dicoding.githubuseraaz.data.response.ItemsItem
import com.dicoding.githubuseraaz.data.response.ItemsResponse
import com.dicoding.githubuseraaz.databinding.ActivityMainBinding
import com.dicoding.githubuseraaz.ui.detailuser.DetailUserActivity
import com.dicoding.githubuseraaz.ui.favorite.FavoriteActivity
import com.dicoding.githubuseraaz.ui.settings.SettingsActivity
import com.dicoding.githubuseraaz.utils.SettingPreferences
import com.dicoding.githubuseraaz.utils.dataStore
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), UserAdapter.OnUserClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var settingPreferences: SettingPreferences
    private lateinit var viewModelFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Updated version
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        settingPreferences = SettingPreferences.getInstance(applicationContext.dataStore)

        lifecycleScope.launch {
            settingPreferences.getThemeSetting().collect { isDarkModeActive ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
        viewModelFactory = MainViewModelFactory(settingPreferences)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.userList.observe(this) { userList ->
            adapter.submitList(userList)
            binding.progressBar.visibility = View.GONE
        }

        binding.favoriteButton.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        binding.settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        adapter = UserAdapter(this)
        binding.rvListUser.layoutManager = LinearLayoutManager(this)
        binding.rvListUser.adapter = adapter

        binding.progressBar.visibility = View.VISIBLE

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, actionId, _ ->
                searchBar.setText(searchView.text)
                searchView.hide()
                viewModel.setSearchQuery(searchView.text.toString())
                binding.progressBar.visibility = View.VISIBLE
                fetchData()
                false
            }
        }
        fetchData()
    }

    private fun fetchData() {
        viewModel.fetchData("a")
    }

    override fun onUserClick(user: ItemsItem) {
        val username = user.login ?: ""
        onButtonClick(username)
    }

    private fun onButtonClick(username: String) {
        val intent = Intent(this, DetailUserActivity::class.java)
        intent.putExtra("USERNAME", username)
        startActivity(intent)
    }
}
