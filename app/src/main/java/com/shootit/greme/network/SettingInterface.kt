package com.shootit.greme.network

import com.shootit.greme.model.UserAreaAndPurposeInfo
import com.shootit.greme.model.UserCurrentInfo
import com.shootit.greme.model.UserInterestAndGenderInfo
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
}