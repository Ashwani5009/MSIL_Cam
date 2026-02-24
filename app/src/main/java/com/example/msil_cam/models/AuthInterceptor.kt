package com.example.msil_cam.models

import android.content.Context
import com.example.msil_cam.AuthKeys
import com.example.msil_cam.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        // Read token from datastore
        val token = runBlocking {
            context.dataStore.data.first()[AuthKeys.TOKEN]
        }

        // original request
        val requestBuilder = chain.request().newBuilder()

        token?.let {
            requestBuilder.addHeader(
                "Authorization",
                "Bearer $it"
            )
        }

        return chain.proceed(requestBuilder.build())
    }
}