package com.example.msil_cam.models

import com.example.msil_cam.Login

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val data : LoginResponse) : LoginUiState()
    data class Error(val message : String) : LoginUiState()
}