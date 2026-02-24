package com.example.msil_cam.models

import android.app.Application

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContextProvider.initialize(this)
    }
}