package com.example.msil_cam

import android.app.Application
import androidx.activity.result.PickVisualMediaRequest
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.msil_cam.database.EmployeeDatabase
import com.example.msil_cam.models.AuthApi
import com.example.msil_cam.models.Employee
import com.example.msil_cam.models.LoginResponse
import com.example.msil_cam.models.LoginUiState
import com.example.msil_cam.models.ProfileResponse
import com.example.msil_cam.models.RetrofitClient
import com.example.msil_cam.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    val repository: AuthRepository
    private val _loginState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    private val _profileData = MutableStateFlow<ProfileResponse?>(null)
    val loginState: StateFlow<LoginUiState> = _loginState.asStateFlow()
    val profileData: StateFlow<ProfileResponse?> = _profileData.asStateFlow()

    init {
        val dao = EmployeeDatabase.getInstance(application).getEmployeeDao()
        val api = RetrofitClient.api
        repository = AuthRepository(dao,api,application)
    }

    fun login(staffId: String, dob: String){
        viewModelScope.launch {
            _loginState.value = LoginUiState.Loading
            val result = repository.login(staffId,dob)
            if(result.isSuccess) {
                val response = result.getOrNull()
                if(response != null){
                    _loginState.value = LoginUiState.Success(response)
                } else {
                    _loginState.value = LoginUiState.Error("Invalid Staff ID or Dob")
                }
            } else {
                _loginState.value = LoginUiState.Error(result.exceptionOrNull()?.message ?: "Login Failed")
            }
        }
    }

    fun loadProfile() {
        viewModelScope.launch {
            val result = repository.getProfile()
            if(result.isSuccess){
                _profileData.value = result.getOrNull()
            } else {
                _profileData.value = null
            }
        }
    }

    fun insert(employee: Employee) = viewModelScope.launch {
        repository.insert(employee)
    }
}