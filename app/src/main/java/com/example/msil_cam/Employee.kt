package com.example.msil_cam

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employee_table")
data class Employee(
    val staffId: String,
    val dob: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
