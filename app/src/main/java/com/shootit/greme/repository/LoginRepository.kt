package com.shootit.greme.repository

import android.app.Application
import android.util.Log
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
        val response = ConnectionObject.getRetrofitService.getLoginData(domain)
        return if (response.isSuccessful) LoginCheckData(
            response.body()!!.isExistingUser?: false,
            response.body()!!.email?: "",
            response.body()!!.accessToken?: ""
        )
        else {
            Log.d("login error", response.errorBody().toString())
            LoginCheckData(false, "", "")
        }
    }
}