package com.shootit.greme.repository

import android.app.Application
import android.util.Log
import com.shootit.greme.model.LoginData
import com.shootit.greme.network.ConnectionObject

class LoginRepository() {
    companion object {
        private var instance: LoginRepository? = null

        fun getInstance(application: Application): LoginRepository? {
            if(instance == null) instance = LoginRepository()
            return instance
        }
    }

    suspend fun getLoginData(domain: String): LoginData {
        val response = ConnectionObject.getRetrofitService.getEmail(domain)
        return if (response.isSuccessful) LoginData(response.body()!!.email)
        else LoginData("")
    }
}