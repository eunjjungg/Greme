package com.shootit.greme.repository

import android.app.Application
import com.shootit.greme.model.LoginCheckData
import com.shootit.greme.network.ConnectionObject

class LoginRepository() {
    companion object {
        private var instance: LoginRepository? = null

        fun getInstance(application: Application): LoginRepository? {
            if(instance == null) instance = LoginRepository()
            return instance
        }
    }

    suspend fun getLoginData(domain: String): LoginCheckData {
        val response = ConnectionObject.getRetrofitService.getEmail(domain)
        return if (response.isSuccessful) LoginCheckData(response.body()!!.email?: "")
        else LoginCheckData("")
    }
}