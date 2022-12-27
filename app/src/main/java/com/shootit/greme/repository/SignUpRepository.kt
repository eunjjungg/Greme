package com.shootit.greme.repository

import android.app.Application
import android.util.Log
import com.shootit.greme.model.LoginCheckData
import com.shootit.greme.network.ConnectionObject

class SignUpRepository {
    companion object {
        private var instance: SignUpRepository? = null

        fun getInstance(): SignUpRepository? {
            if(instance == null) instance = SignUpRepository()
            return instance
        }
    }

    suspend fun checkUserNameDuplicated(userName: String): Boolean {
        val response = ConnectionObject
            .getSignUpRetrofitService.checkUserNameDuplicated(userName)
        return if (response.isSuccessful) true
        else {
            Log.d("name error", response.errorBody().toString())
            false
        }
    }
}