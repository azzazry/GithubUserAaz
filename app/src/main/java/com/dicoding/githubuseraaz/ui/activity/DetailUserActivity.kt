package com.dicoding.githubuseraaz.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.dicoding.githubuseraaz.R
import com.dicoding.githubuseraaz.adapter.SectionsPagerAdapter
import com.dicoding.githubuseraaz.data.response.ItemsItem
import com.dicoding.githubuseraaz.databinding.ActivityDetailUserBinding
import com.dicoding.githubuseraaz.ui.fragment.FollowFragment
import com.dicoding.githubuseraaz.ui.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel: DetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // updated version
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val username = intent.getStringExtra("USERNAME") ?: ""

        viewModel.detailUser.observe(this) { user ->
            updateUI(user ?: return@observe)
            binding.progressBar.visibility = View.GONE
        }

        binding.progressBar.visibility = View.VISIBLE
        viewModel.fetchUserDetail(username)

        val viewPager2 = binding.viewPager2
        val tabLayout = binding.tabLayout

        val sectionsPagerAdapter = SectionsPagerAdapter(this, listOf(FollowFragment(), FollowFragment()), username)

        viewPager2.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Followers"
                1 -> tab.text = "Following"
            }
        }.attach()
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(user: ItemsItem) {
        Glide.with(this@DetailUserActivity)
            .load(user.avatarUrl)
            .centerCrop()
            .into(binding.tvAvatar)

        binding.tvUsername.text = user.login
        binding.name.text = user.name
        binding.followers.text = "Followers: ${user.followers}"
        binding.following.text = "Following: ${user.following}"
    }
}