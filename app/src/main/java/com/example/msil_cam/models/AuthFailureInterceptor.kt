package com.example.msil_cam.models

import android.content.Context
import android.content.Intent
import androidx.datastore.preferences.core.edit
import com.example.msil_cam.dataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthFailureInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if(response.code == 401) {
            runBlocking {
                context.dataStore.edit { preferences ->
                    preferences.clear()
                }
            }

            // broadcast to notify app
            val intent = Intent("ACTION_LOGOUT")
            context.sendBroadcast(intent)
        }
        return response
    }
}