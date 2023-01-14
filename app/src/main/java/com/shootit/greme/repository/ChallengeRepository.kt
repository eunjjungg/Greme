package com.shootit.greme.repository

import android.util.Log
import com.shootit.greme.model.ChallengeActivityModel
import com.shootit.greme.network.ConnectionObject
import retrofit2.http.Path

class ChallengeRepository {

    companion object {
        private var instance: ChallengeRepository? = null

        fun getInstance(): ChallengeRepository? {
            if(instance == null) instance = ChallengeRepository()
            return instance
        }
    }

    // 챌린지 메인 화면
    suspend fun getMyChallengeList(): ChallengeActivityModel? {
        val response = ConnectionObject
            .getChallengeRetrofitService.getMyChallengeList()
        return if (response.isSuccessful) {
            response.body() as ChallengeActivityModel
        } else {
            Log.d("challenge server err", response.errorBody()?.string().toString())
            null
        }
    }

    // 챌린지 등록
    suspend fun participateChallenge(challengeId: Int) {

    }

    // 챌린지 제외
    suspend fun exceptChallenge(challengeId: Int) {

    }


    // 챌린지 클릭 시 챌린지 정보 & 참여 목록 조회
    suspend fun getChallengeInfo(challengeId: Int) {

    }


    // 홈 화면 챌린지 목록

}