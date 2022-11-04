package com.andyra.submission1ivanandyramadhan.Api

import com.andyra.submission1ivanandyramadhan.BuildConfig
import com.andyra.submission1ivanandyramadhan.Data.Remote.FollowData
import com.andyra.submission1ivanandyramadhan.Data.Remote.ListProfile
import com.andyra.submission1ivanandyramadhan.Data.Remote.ProfileData
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {

    @GET("search/users")
    @Headers("Authorization: token " +BuildConfig.LIMITKEY)
    fun getSearch(
        @Query("q") login: String
    ): Call<ListProfile>

    @GET("users/{login}")
    @Headers("Authorization: token " +BuildConfig.LIMITKEY)
    fun getDetail(
        @Path("login") login: String

    ): Call<ProfileData>

    @GET("users/{login}/following")
    @Headers("Authorization: token " +BuildConfig.LIMITKEY)
    fun getFollowing(
        @Path("login") login: String
    ): Call<FollowData>

    @GET("users/{login}/followers")
    @Headers("Authorization: token " +BuildConfig.LIMITKEY)
    fun getFollowers(
        @Path("login") login: String
    ): Call<FollowData>

}