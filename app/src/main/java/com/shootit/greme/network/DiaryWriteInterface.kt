package com.shootit.greme.network

import com.shootit.greme.model.DiaryDeleteData
import com.shootit.greme.model.DiaryWriteData
import com.shootit.greme.model.ResponseDiaryWriteData
import com.shootit.greme.model.ResponseEntireDiaryData
import retrofit2.Call
import retrofit2.http.*

interface DiaryWriteInterface {
    // 다이어리 작성 => 아직
    @POST("/post")
    fun diaryWrite(@Body data : DiaryWriteData) : Call<ResponseDiaryWriteData>

    // 다이어리 삭제 => 아직
    @HTTP(method = "DELETE", path = "/post", hasBody = true)
    fun diaryDelete(@Body data : DiaryDeleteData) : Call<ResponseDiaryWriteData>

    // 전체 다이어리 조회
    @GET("/post/all")
    fun entireDiaryLook() : Call<List<ResponseEntireDiaryData>>
}