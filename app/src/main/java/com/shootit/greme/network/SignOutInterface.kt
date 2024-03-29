package com.shootit.greme.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.PATCH
import retrofit2.http.Path

interface SignOutInterface {
    // 회원 탈퇴
    @DELETE("/user")
    fun signOut() : Call<Void>
}