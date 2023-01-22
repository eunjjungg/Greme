package com.shootit.greme.network

import com.shootit.greme.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface SettingInterface {

    // get current profile
    @GET("/user/profile")
    suspend fun getCurrentProfile() : Response<UserCurrentInfo>

    // set interest, gender
    @PATCH("/user/profile1")
    suspend fun setUserInterestAndGender(@Body data: UserInterestAndGenderInfo) : Response<Void>

    // set area, purpose
    @PATCH("/user/profile2")
    suspend fun setUserAreaAndPurpose(@Body data: UserAreaAndPurposeInfo) : Response<Void>

    // 환경 설정 첫 화면
    @GET("/user")
    fun getSettigInfo() : Call<ResponseSettingInfoData>
}