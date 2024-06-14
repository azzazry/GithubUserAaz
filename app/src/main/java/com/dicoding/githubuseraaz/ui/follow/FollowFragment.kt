package com.dicoding.githubuseraaz.ui.follow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuseraaz.adapter.UserAdapter
import com.dicoding.githubuseraaz.data.response.ItemsItem
import com.dicoding.githubuseraaz.data.response.ItemsResponse
import com.dicoding.githubuseraaz.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private lateinit var adapter: UserAdapter
    private val viewModel: FollowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabPosition = arguments?.getInt(ARG_POSITION) ?: 1
        val username = arguments?.getString(ARG_USERNAME) ?: ""

        adapter = UserAdapter(object : UserAdapter.OnUserClickListener{
            override fun onUserClick(user: ItemsItem) {
            }
        })

        binding.rvFollowList.adapter = adapter
        binding.rvFollowList.layoutManager = LinearLayoutManager(requireContext())

        if (tabPosition == 1) viewModel.fetchFollowers(username) else viewModel.fetchFollowing(username)

        viewModel.followList.observe(viewLifecycleOwner) {userList ->
            adapter.submitList(userList)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) binding.progressBar.visibility = View.VISIBLE else binding.progressBar.visibility = View.INVISIBLE
        }
    }


    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

}