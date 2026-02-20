package com.example.msil_cam

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmployeeDao {
    @Insert
    suspend fun insert(employee: Employee)
    @Query("""
       SELECT * FROM employee_table
        WHERE staffId = :staffId AND dob = :dob
        LIMIT 1
    """)
    suspend fun login(staffId: String,dob: String) : Employee?
}