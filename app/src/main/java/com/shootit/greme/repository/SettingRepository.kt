package com.shootit.greme.repository

import android.util.Log
import com.shootit.greme.model.ChallengeActivityModel
import com.shootit.greme.model.UserAreaAndPurposeInfo
import com.shootit.greme.model.UserCurrentInfo
import com.shootit.greme.model.UserInterestAndGenderInfo
import com.shootit.greme.network.ConnectionObject

class SettingRepository {

    companion object {
        private var instance: SettingRepository? = null

        fun getInstance(): SettingRepository? {
            if(instance == null) instance = SettingRepository()
            return instance
        }
    }

    suspend fun getCurrentProfile(): UserCurrentInfo? {
        val response = ConnectionObject
            .getSettingRetrofitService.getCurrentProfile()
        return if (response.isSuccessful) {
            response.body() as UserCurrentInfo
        } else {
            Log.d("challenge server err", response.errorBody()?.string().toString())
            null
        }
    }

    suspend fun setUserInterestAndGender(userInterestAndGenderInfo: UserInterestAndGenderInfo): Boolean {
        val response = ConnectionObject
            .getSettingRetrofitService.setUserInterestAndGender(userInterestAndGenderInfo)
        return response.isSuccessful
    }

    suspend fun setUserAreaAndPurpose(userAreaAndPurposeInfo: UserAreaAndPurposeInfo): Boolean {
        val response = ConnectionObject
            .getSettingRetrofitService.setUserAreaAndPurpose(userAreaAndPurposeInfo)
        return response.isSuccessful
    }
}