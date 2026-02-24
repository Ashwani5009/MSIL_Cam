package com.example.msil_cam.models

data class LoginResponse(
    val token: String,
    val user: UserDto
)
