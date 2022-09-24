package com.andyra.submission1ivanandyramadhan.Data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileData(
	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("avatar_url")
	val avatarUrl: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("login")
	val login: String? = null,

	@field:SerializedName("location")
	val location: String? = null,

	@field:SerializedName("company")
	val company: String? = null,

	@field:SerializedName("following")
	val following: Int? = null,

	@field:SerializedName("followers")
	val followers: Int? = null,

	@field:SerializedName("public_repos")
	val publicRepos: Int? = null
) : Parcelable
