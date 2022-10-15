package com.andyra.submission1ivanandyramadhan.Api

import com.andyra.submission1ivanandyramadhan.Data.Remote.FollowData
import com.andyra.submission1ivanandyramadhan.Data.Remote.ListProfile
import com.andyra.submission1ivanandyramadhan.Data.Remote.ProfileData
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    @GET("search/users")
    fun getSearch(
        @Query("q") login: String
    ): Call<ListProfile>

    @GET("users/{login}")
    fun getDetail(
        @Path("login") login: String
    ): Call<ProfileData>

    @GET("users/{login}/following")
    fun getFollowing(
        @Path("login") login: String
    ): Call<FollowData>

    @GET("users/{login}/followers")
    fun getFollowers(
        @Path("login") login: String
    ): Call<FollowData>

}