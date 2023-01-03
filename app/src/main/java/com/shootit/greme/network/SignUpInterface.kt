package com.shootit.greme.network

import com.shootit.greme.model.SignUpAccountData
import com.shootit.greme.model.UserAdditionalInfo
import com.shootit.greme.model.UserInterestData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface SignUpInterface {
    // 유저 닉네임 중복 조회
    @GET("/user/username/check")
    suspend fun checkUserNameDuplicated(@Query("username") userName: String) : Response<Void>

    // 유저 회원가입
    @POST("/user/sign-up")
    suspend fun makeAccount(@Body data: SignUpAccountData) : Response<Void>

    // 유저 관심사 수정
    @PATCH("/user/interest")
    suspend fun setInterest(@Body data: UserInterestData) : Response<Void>

    // 유저 정보 확인
    @PATCH("/user/info")
    suspend fun setAdditionalInfo(@Body data: UserAdditionalInfo) : Response<Void>
}