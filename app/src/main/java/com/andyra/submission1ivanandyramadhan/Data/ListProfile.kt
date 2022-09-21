package com.andyra.submission1ivanandyramadhan.Data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListProfile(
    var photo: Int?,
    var name: String?,
    var username: String?,
    var location: String?,
    var company: String?,
    var follower: String?,
    var following: String?,
    var repo: String?
) : Parcelable
