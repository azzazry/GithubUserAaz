package com.dicoding.githubuseraaz.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuseraaz.R
import com.dicoding.githubuseraaz.adapter.UserAdapter
import com.dicoding.githubuseraaz.data.response.ItemsItem
import com.dicoding.githubuseraaz.data.response.ItemsResponse
import com.dicoding.githubuseraaz.databinding.ActivityFavoriteBinding
import com.dicoding.githubuseraaz.ui.detailuser.DetailUserActivity

class FavoriteActivity : AppCompatActivity(), UserAdapter.OnUserClickListener {
    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Updated version
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this)

        val repository = FavoriteRepository(applicationContext)
        val viewModelFactory = FavoriteViewModelFactory(repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(FavoriteViewModel::class.java)

        adapter = UserAdapter(this)
        binding.recyclerView.adapter = adapter

        viewModel.getFavoriteUsers().observe(this) { users ->
            val items = arrayListOf<ItemsItem>()
            users.map {
                val item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl)
                items.add(item)
            }
            adapter.submitList(items)
        }
    }

    override fun onUserClick(user: ItemsItem) {
        val intent = Intent(this@FavoriteActivity, DetailUserActivity::class.java)
        intent.putExtra("USERNAME", user.login)
        startActivity(intent)
    }
}