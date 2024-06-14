package com.dicoding.githubuseraaz.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.githubuseraaz.R
import com.dicoding.githubuseraaz.data.response.ItemsItem
import com.dicoding.githubuseraaz.databinding.ListUserBinding

class UserAdapter(private val onUserClickListener: OnUserClickListener
): ListAdapter<ItemsItem, UserAdapter.viewHolder>(DIFF_CALLBACK) {
    inner class viewHolder(binding: ListUserBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(user: ItemsItem) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .apply(RequestOptions().override(100, 100))
                    .into(findViewById(R.id.avatar))

                findViewById<TextView>(R.id.username).text = user.login
            }
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val user = getItem(position)
                onUserClickListener.onUserClick(user)
            }
        }
    }

    interface OnUserClickListener {
        fun onUserClick(user: ItemsItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.viewHolder {
        val binding = ListUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserAdapter.viewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}