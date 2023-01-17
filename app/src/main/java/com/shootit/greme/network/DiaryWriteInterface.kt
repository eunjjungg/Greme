package com.shootit.greme.network

import com.shootit.greme.model.*
import retrofit2.Call
import retrofit2.http.*

interface DiaryWriteInterface {
    // 다이어리 작성 => 아직
    @POST("/post")
    fun diaryWrite(@Body data : DiaryWriteData) : Call<ResponseDiaryWriteData>

    // 다이어리 삭제
    @HTTP(method = "DELETE", path = "/post", hasBody = true)
    fun diaryDelete(@Body data : DiaryDeleteData) : Call<Void>

    // 전체 다이어리 조회
    @GET("/post/all")
    fun entireDiaryLook() : Call<List<ResponseEntireDiaryData>>

    // 날짜로 다이어리 조회 => 아직
    @GET("/post/date/{date}")
    fun dateDiaryLook(@Path("date") date: String) : Call<ResponseDateDiaryData>
}