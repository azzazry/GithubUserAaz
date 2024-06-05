package com.dicoding.githubuseraaz.data.response

import com.google.gson.annotations.SerializedName

data class ItemsItem(

	@field:SerializedName("gists_url")
	val gistsUrl: String? = null,

	@field:SerializedName("followers")
	val followers: String? = null,

	@field:SerializedName("following")
	val following: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,
)