package com.dicoding.myapplication2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsername(
        @Query("q") query: String
    ): Call<UsernameResponse>

    @GET("users/{username}")
    fun getUserProfile(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    /*@GET("users/{username}/followers")
    fun getFollowers(
        @Path("followers") followers: String
    ): Call */
}