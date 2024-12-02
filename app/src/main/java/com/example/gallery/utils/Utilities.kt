package com.example.gallery.utils

import android.content.SharedPreferences
import android.util.Log

object Utilities {
    private const val FLAG = "database_sync"
    fun setFlag(prefer: SharedPreferences) {
        Log.d("SharedPreference", "set: $prefer")
        prefer.edit().putBoolean(FLAG, true).apply()
    }
    fun getFlag(prefer: SharedPreferences): Boolean {
        Log.d("SharedPreference", "get: $prefer")
        return prefer.getBoolean(FLAG, false) // Default value is false
    }
    fun clearFlag(prefer: SharedPreferences) {
        Log.d("SharedPreference", "clear: $prefer")
        prefer.edit().remove(FLAG).apply()
        // prefer.edit().putBoolean(FLAG, false).apply()
    }
}