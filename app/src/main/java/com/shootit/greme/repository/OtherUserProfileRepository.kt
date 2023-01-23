package com.shootit.greme.repository

import com.shootit.greme.model.OtherUserProfileModel
import com.shootit.greme.network.ConnectionObject

class OtherUserProfileRepository {

    companion object {
        private var instance: OtherUserProfileRepository? = null

        fun getInstance(): OtherUserProfileRepository? {
            if(instance == null) instance = OtherUserProfileRepository()
            return instance
        }
    }

    // 다른 유저 프로필 조회
    suspend fun getOtherUserProfile(userId: Int): OtherUserProfileModel? {
        val response = ConnectionObject
            .getOtherUserProfileRetrofitService.getOtherUserProfile(userId)
        return if (response.isSuccessful) {
            response.body() as OtherUserProfileModel
        } else {
            null
        }
    }
}