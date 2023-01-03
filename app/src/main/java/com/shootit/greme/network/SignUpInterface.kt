package com.shootit.greme.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SignUpInterface {
    // 유저 닉네임 중복 조회
    @GET("/user/username/check")
    suspend fun checkUserNameDuplicated(@Query("username") userName: String) : Response<Void>
}