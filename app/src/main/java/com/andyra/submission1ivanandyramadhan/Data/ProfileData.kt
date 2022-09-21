package com.andyra.submission1ivanandyramadhan.Data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class ListData(
	@field:SerializedName("item")
	val item: ProfileData
)

@Parcelize
data class ProfileData(
	@field:SerializedName("id")
	val id: Int?,

	@field:SerializedName("avatar_url")
	val avatarUrl: String?,

	@field:SerializedName("name")
	val name: String?,

	@field:SerializedName("login")
	val login: String?,

	@field:SerializedName("location")
	val location: String?,

	@field:SerializedName("company")
	val company: String?,

	@field:SerializedName("following")
	val following: Int?,

	@field:SerializedName("followers")
	val followers: Int?,

	@field:SerializedName("public_repos")
	val publicRepos: Int?
) : Parcelable
