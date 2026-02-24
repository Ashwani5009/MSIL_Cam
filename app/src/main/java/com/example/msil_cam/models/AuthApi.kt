package com.example.msil_cam.models

import com.example.msil_cam.Login
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {

    @POST("/login")
    suspend fun login(@Body employee: Employee) : LoginResponse

    @GET("/profile")
    suspend fun getProfile() : ProfileResponse
}