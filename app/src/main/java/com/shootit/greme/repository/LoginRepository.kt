package com.shootit.greme.repository

import android.app.Application
import android.util.Log
import com.shootit.greme.model.LoginData
import com.shootit.greme.network.ConnectionObject

class LoginRepository(application: Application) {
    companion object {
        private var instance: LoginRepository? = null

        fun getInstance(application: Application): LoginRepository? {
            if(instance == null) instance = LoginRepository(application)
            return instance
        }
    }

    suspend fun getLoginData(): LoginData {
        val response = ConnectionObject.getRetrofitService.getEmail()
        return if (response.isSuccessful) LoginData(response.body()!!.email)
        else LoginData("")
    }
}