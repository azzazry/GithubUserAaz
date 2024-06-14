package com.dicoding.githubuseraaz.ui.detailuser

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.githubuseraaz.R
import com.dicoding.githubuseraaz.adapter.SectionsPagerAdapter
import com.dicoding.githubuseraaz.data.response.ItemsResponse
import com.dicoding.githubuseraaz.database.AppDatabase
import com.dicoding.githubuseraaz.database.FavoriteUserDao
import com.dicoding.githubuseraaz.databinding.ActivityDetailUserBinding
import com.dicoding.githubuseraaz.ui.follow.FollowFragment
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var factory: DetailViewModelFactory
    private lateinit var favoriteUserDao: FavoriteUserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        favoriteUserDao = AppDatabase.getInstance(applicationContext).favoriteUserDao()

        factory = DetailViewModelFactory(favoriteUserDao)

        viewModel = ViewModelProvider(this, factory).get(DetailViewModel::class.java)
        val username = intent.getStringExtra("USERNAME") ?: ""

        viewModel.userDetail.observe(this) { user ->
            if (user != null) {
                updateUI(user)
                binding.progressBar.visibility = View.GONE
            }
        }

        binding.progressBar.visibility = View.VISIBLE
        viewModel.fetchUserDetail(username)

        val viewPager2 = binding.viewPager2
        val tabLayout = binding.tabLayout

        val sectionsPagerAdapter =
            SectionsPagerAdapter(this, listOf(FollowFragment(), FollowFragment()), username)

        viewPager2.adapter = sectionsPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Follower"
                1 -> tab.text = "Following"
            }
        }.attach()

        binding.toggleFavorite.setOnCheckedChangeListener { _, isChecked ->
            val user = viewModel.userDetail.value
            val username = user?.login ?: ""
            if (isChecked) {
                user?.let { viewModel.addToFavorites(it) }
                binding.toggleFavorite.setBackgroundResource(R.drawable.favorite_toggle)
            } else {
                viewModel.removeFromFavorites(username)
                binding.toggleFavorite.setBackgroundResource(R.drawable.non_favorite_toggle)
            }
        }

        viewModel.isFavorite.observe(this) { isFavorite ->
            if (isFavorite) {
                binding.toggleFavorite.isChecked = true
                binding.toggleFavorite.setBackgroundResource(R.drawable.favorite_toggle)
            } else {
                binding.toggleFavorite.isChecked = false
                binding.toggleFavorite.setBackgroundResource(R.drawable.non_favorite_toggle)
            }
        }
    }

    private fun updateUI(user: ItemsResponse) {
        Glide.with(this).load(user.avatarUrl).centerCrop().into(binding.tvAvatar)

        binding.tvUsername.text = user.login
        binding.name.text = user.name
        "Followers: ${user.followers}".also { binding.followers.text = it }
        "Following: ${user.following}".also { binding.following.text = it }
    }
}