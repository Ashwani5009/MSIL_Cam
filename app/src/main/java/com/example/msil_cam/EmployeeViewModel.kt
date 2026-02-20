package com.example.msil_cam

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.msil_cam.database.EmployeeDatabase
import com.example.msil_cam.repository.EmployeeRepository
import kotlinx.coroutines.launch

class EmployeeViewModel(application: Application) : AndroidViewModel(application) {
    val repository: EmployeeRepository
    private val _loginResult = MutableLiveData<Employee?>()
    val loginResult: LiveData<Employee?> = _loginResult

    init {
        val dao = EmployeeDatabase.getInstance(application).getEmployeeDao()
        repository = EmployeeRepository(dao)
    }

    fun login(staffId: String, dob: String){
        viewModelScope.launch {
            val employee = repository.login(staffId,dob)
            _loginResult.value = employee
        }
    }

    fun insert(employee: Employee) = viewModelScope.launch {
        repository.insert(employee)
    }
}