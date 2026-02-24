package com.example.msil_cam.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.msil_cam.AuthKeys
import com.example.msil_cam.models.Employee
import com.example.msil_cam.EmployeeDao
import com.example.msil_cam.Login
import com.example.msil_cam.dataStore
import com.example.msil_cam.models.AuthApi
import com.example.msil_cam.models.LoginResponse
import com.example.msil_cam.models.ProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository(private val dao: EmployeeDao,private val api: AuthApi,private val context: Context) {
    suspend fun insert(employee: Employee) = dao.insert(employee)
//    suspend fun login(staffId: String,dob: String) : Employee? {
//        return dao.login(staffId,dob)
//    }

    suspend fun login(staffId: String,dob: String) : Result<LoginResponse> {
        return try {
            val response = api.login(Employee(staffId,dob))
            saveLoginSession(response.token)
            Result.success(response)
        } catch (e : Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveLoginSession(token: String) {
        context.dataStore.edit { prefs->
            prefs[AuthKeys.TOKEN] = token
            prefs[AuthKeys.IS_LOGGED_IN] = true
        }
    }

    suspend fun getProfile() : Result<ProfileResponse> {
        return try {
            val response = api.getProfile()
            Result.success(response)
        } catch (e : Exception){
            Result.failure(e)
        }
    }
}