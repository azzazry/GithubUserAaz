package com.dicoding.githubuseraaz.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuseraaz.R
import com.dicoding.githubuseraaz.adapter.UserAdapter
import com.dicoding.githubuseraaz.data.response.ItemsItem
import com.dicoding.githubuseraaz.databinding.ActivityMainBinding
import com.dicoding.githubuseraaz.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), UserAdapter.OnUserClickListener {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: UserAdapter
    private val viewModel: MainViewModel by viewModels()

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

        viewModel.userList.observe(this) { userList ->
            adapter.submitList(userList)
            binding.progressBar.visibility = View.GONE
        }

        adapter = UserAdapter(this)
        binding.rvListUser.layoutManager = LinearLayoutManager(this)
        binding.rvListUser.adapter = adapter

        binding.progressBar.visibility = View.VISIBLE

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { v, actionId, event ->
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
        viewModel.fetchData("asep")
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