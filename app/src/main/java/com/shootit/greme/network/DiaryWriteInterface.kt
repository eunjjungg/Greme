package com.shootit.greme.network

import com.shootit.greme.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface DiaryWriteInterface {
    // 다이어리 작성
    @Multipart
    @POST("/post")
    fun diaryWrite(@Part("content") content: String, @Part("hashtag") hashtag: String, @Part("challenge") challenge: Long, @Part("status") status: Boolean, @Part multipartFile : MultipartBody.Part) : Call<Long>

    // 다이어리 삭제
    @HTTP(method = "DELETE", path = "/post", hasBody = true)
    fun diaryDelete(@Body data : DiaryDeleteData) : Call<Void>

    // 전체 다이어리 조회
    @GET("/post/all")
    fun entireDiaryLook() : Call<List<ResponseEntireDiaryData>>

    // 날짜로 다이어리 조회
    @GET("/post/date/{date}")
    fun dateDiaryLook(@Path("date") date: String) : Call<ResponseDateDiaryData>
}