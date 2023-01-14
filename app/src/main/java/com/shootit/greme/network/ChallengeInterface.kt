package com.shootit.greme.network

import com.shootit.greme.model.ChallengeActivityModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ChallengeInterface {
    // 챌린지 메인 화면
    @GET("/challenge/")
    suspend fun getMyChallengeList(): Response<ChallengeActivityModel>

    // 챌린지 등록
    @POST("/challenge/{challengeId}")
    suspend fun participateChallenge(@Path("challengeId") challengeId: Int): Response<Void>

    // 챌린지 제외
    @PATCH("/challenge/{challengeId}")
    suspend fun exceptChallenge(@Path("challengeId") challengeId: Int): Response<Void>

    // 챌린지 클릭 시 챌린지 정보 & 참여 목록 조회
    @GET("/challenge/{challengeId}")
    suspend fun getChallengeInfo(@Path("challengeId") challengeId: Int)

    // 홈 화면 챌린지 목록
    // TODO 백엔드 미구현 이슈
    @GET("")
    suspend fun getChallengeHomeFeedList()
}