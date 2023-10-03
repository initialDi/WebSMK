package com.example.websmk.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface apiService {
    @POST("login")
    fun login(@Body userRequest: UserRequest):
            Call<UserResponse>
}