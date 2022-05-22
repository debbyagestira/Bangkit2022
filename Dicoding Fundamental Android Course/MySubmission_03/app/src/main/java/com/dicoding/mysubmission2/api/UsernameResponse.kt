package com.dicoding.mysubmission2.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class UsernameResponse(

	@field:SerializedName("items")
	val items: List<ItemsItem>
)

@Parcelize
data class ItemsItem(

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val username: String
): Parcelable

@Parcelize
data class DetailUserResponse(

	@field:SerializedName("followers")
	val followers: Int,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("public_repos")
	val publicRepos: Int,

	@field:SerializedName("following_url")
	val followingUrl: String,

	@field:SerializedName("following")
	val following: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("company")
	val company: String?,

	@field:SerializedName("location")
	val location: String?,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("followers_url")
	val followersUrl: String,

	@field:SerializedName("blog")
	val blog: String
): Parcelable