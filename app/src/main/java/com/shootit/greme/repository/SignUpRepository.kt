package com.shootit.greme.repository

import android.app.Application
import android.util.Log
import com.shootit.greme.model.LoginCheckData
import com.shootit.greme.model.SignUpAccountData
import com.shootit.greme.model.UserAdditionalInfo
import com.shootit.greme.model.UserInterestData
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
        return if (response.isSuccessful) {
            Log.d("ccheck", response.code().toString())
            true
        }
        else {
            Log.d("ccheck error", response.code().toString())
            Log.d("name error", response.errorBody().toString())
            false
        }
    }

    suspend fun makeAccount(signUpData: SignUpAccountData): String? {
        val response = ConnectionObject
            .getSignUpRetrofitService.makeAccount(signUpData)

        return if (response.isSuccessful) {
            response.headers().toString()
        } else {
            null
        }
    }

    suspend fun setInterest(userInterestData: UserInterestData): Boolean {
        val response = ConnectionObject
            .getSignUpRetrofitService.setInterest(userInterestData)

        return response.isSuccessful
    }

    suspend fun setAdditionalInfo(userAdditionalInfo: UserAdditionalInfo): Boolean {
        val response = ConnectionObject
            .getSignUpRetrofitService.setAdditionalInfo(userAdditionalInfo)

        return response.isSuccessful
    }
}