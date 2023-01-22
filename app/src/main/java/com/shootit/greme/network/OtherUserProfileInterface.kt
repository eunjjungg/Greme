package com.shootit.greme.network

import com.shootit.greme.model.OtherUserProfileModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface OtherUserProfileInterface {

    @GET("/challenge/profile/{user_id}")
    suspend fun getOtherUserProfile(@Path("user_id") userId: Int): Response<OtherUserProfileModel>
}