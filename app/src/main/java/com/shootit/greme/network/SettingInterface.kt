package com.shootit.greme.network

import com.shootit.greme.model.UserAreaAndPurposeInfo
import com.shootit.greme.model.UserCurrentInfo
import com.shootit.greme.model.UserInterestAndGenderInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET

interface SettingInterface {

    // get current profile
    @GET("/user/profile")
    fun getCurrentProfile() : Response<UserCurrentInfo>

    // set interest, gender
    @GET("/user/profile1")
    fun setUserInterestAndGender(@Body data: UserInterestAndGenderInfo) : Response<Void>

    // set area, purpose
    @GET("/user/profile2")
    fun setUserAreaAndPurpose(@Body data: UserAreaAndPurposeInfo) : Response<Void>
}