package com.shootit.greme.network

import com.shootit.greme.model.DiaryDeleteData
import com.shootit.greme.model.DiaryWriteData
import com.shootit.greme.model.ResponseDiaryWriteData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DiaryWriteInterface {
    // 다이어리 작성
    @POST("/post")
    fun diaryWrite(@Body data : DiaryWriteData) : Call<ResponseDiaryWriteData>

    // 다이어리 삭제
    @POST("/post")
    fun diaryDelete(@Body data : DiaryDeleteData) : Call<ResponseDiaryWriteData>
}