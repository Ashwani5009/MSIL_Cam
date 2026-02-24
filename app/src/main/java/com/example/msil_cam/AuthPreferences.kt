package com.example.msil_cam

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "auth_prefs")

object AuthKeys {
    val TOKEN = stringPreferencesKey("jwt_token")
    val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
}