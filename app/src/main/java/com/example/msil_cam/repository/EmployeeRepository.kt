package com.example.msil_cam.repository

import androidx.lifecycle.LiveData
import com.example.msil_cam.Employee
import com.example.msil_cam.EmployeeDao

class EmployeeRepository(private val dao: EmployeeDao) {
    suspend fun insert(employee: Employee) = dao.insert(employee)
    suspend fun login(staffId: String,dob: String) : Employee? {
        return dao.login(staffId,dob)
    }
}