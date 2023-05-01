package com.example.storyapp.apihelper

import com.example.storyapp.data.AllStoryResponse
import com.example.storyapp.data.LoginResponse
import com.example.storyapp.data.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ):Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @GET("stories")
    fun allStory(
        @Header("Authorization") token: String
    ): Call<AllStoryResponse>

}