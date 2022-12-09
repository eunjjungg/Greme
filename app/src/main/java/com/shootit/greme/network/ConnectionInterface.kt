package com.shootit.greme.network

import com.shootit.greme.model.LoginData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ConnectionInterface {

    // login (header에 token 삽입)
    @GET("/oauth2/login")
    suspend fun getEmail(@Query("domain") domain: String): Response<LoginData>
}