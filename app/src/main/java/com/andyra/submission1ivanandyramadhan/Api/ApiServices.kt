package com.andyra.submission1ivanandyramadhan.Api

import com.andyra.submission1ivanandyramadhan.Data.ListData
import com.andyra.submission1ivanandyramadhan.Data.ProfileData
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    @GET("search/users?q={login}")
    fun getSearch(
        @Path("login") items: String
    ): Call<ListData>

    @GET("users/{login}")
    fun getDetail(
        @Path("login") login: String
    ): Call<ProfileData>
}