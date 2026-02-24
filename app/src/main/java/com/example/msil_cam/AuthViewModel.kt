package com.example.msil_cam

import android.app.Application
import androidx.activity.result.PickVisualMediaRequest
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.msil_cam.database.EmployeeDatabase
import com.example.msil_cam.models.AuthApi
import com.example.msil_cam.models.Employee
import com.example.msil_cam.models.LoginResponse
import com.example.msil_cam.models.ProfileResponse
import com.example.msil_cam.models.RetrofitClient
import com.example.msil_cam.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    val repository: AuthRepository
    private val _loginResult = MutableLiveData<LoginResponse?>()
    private val _profileData = MutableLiveData<ProfileResponse?>()
    val loginResult: LiveData<LoginResponse?> = _loginResult
    val profileData: LiveData<ProfileResponse?> = _profileData

    init {
        val dao = EmployeeDatabase.getInstance(application).getEmployeeDao()
        val api = RetrofitClient.api
        repository = AuthRepository(dao,api,application)
    }

    fun login(staffId: String, dob: String){
        viewModelScope.launch {
            val result = repository.login(staffId,dob)
            if(result.isSuccess) {
                _loginResult.value = result.getOrNull()
            } else {
                _loginResult.value = null
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