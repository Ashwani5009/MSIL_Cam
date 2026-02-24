package com.example.msil_cam.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.msil_cam.models.Employee
import com.example.msil_cam.EmployeeDao

@Database(entities = [Employee::class] , version = 1 , exportSchema = true)
abstract class EmployeeDatabase : RoomDatabase() {
    abstract fun getEmployeeDao() : EmployeeDao
    companion object {
        var INSTANCE : EmployeeDatabase? = null

        fun getInstance(context: Context) : EmployeeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmployeeDatabase::class.java,
                    "employee_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}