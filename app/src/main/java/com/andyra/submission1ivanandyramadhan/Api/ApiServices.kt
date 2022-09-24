package com.andyra.submission1ivanandyramadhan.Api

import com.andyra.submission1ivanandyramadhan.Data.ListProfile
import com.andyra.submission1ivanandyramadhan.Data.ProfileData
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

}