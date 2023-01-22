package com.shootit.greme.util

import android.content.SharedPreferences

class EncryptedSpfImpl (
    private val encryptedSpf: SharedPreferences
) {
    companion object {
        const val DEFAULT_STRING = ""
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
        const val USER_EMAIL = "USER_EMAIL"
    }

    fun getAccessToken(): String? {
        return encryptedSpf.getString(ACCESS_TOKEN, DEFAULT_STRING)
    }

    fun setAccessToken(token: String) {
        encryptedSpf.edit().putString(ACCESS_TOKEN, token).apply()
    }

    fun getUserEmail(): String? {
        return encryptedSpf.getString(USER_EMAIL, DEFAULT_STRING)
    }

    fun setUserEmail(email: String) {
        encryptedSpf.edit().putString(USER_EMAIL, email).apply()
    }
}