package com.andyra.submission1ivanandyramadhan.Data.Remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListProfile(
    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("items")
    val items: ArrayList<Items>
) : Parcelable

@Parcelize
data class Items(
    @field:SerializedName("avatar_url")
    val avatarUrl: String?,

    @SerializedName("login")
    val login: String?,
) : Parcelable
