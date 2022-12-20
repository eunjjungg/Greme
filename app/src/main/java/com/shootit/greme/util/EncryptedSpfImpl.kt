package com.shootit.greme.util

import android.content.SharedPreferences

class EncryptedSpfImpl (
    private val encryptedSpf: SharedPreferences
) {
    companion object {
        const val ACCESS_TOKEN = "ACCESS_TOKEN"
    }

    fun getAccessToken(): String? {
        return encryptedSpf.getString(ACCESS_TOKEN, "")
    }

    fun setAccessToken(token: String) {
        encryptedSpf.edit().putString(ACCESS_TOKEN, token).apply()
    }
}