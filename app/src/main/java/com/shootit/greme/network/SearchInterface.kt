package com.shootit.greme.network

import com.shootit.greme.model.ResponseOtherUserDiaryData
import com.shootit.greme.model.ResponseSearchData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SearchInterface {
    // 다른 유저의 다이어리 조회
    @GET("/post/{postId}")
    fun otherUserDiary(@Path("postId") postId: Int) : Call<ResponseOtherUserDiaryData>

    // 검색으로 다이어리 조회
    @GET("/post")
    fun searchDiary(@Query("search") search: String) : Call<List<ResponseSearchData>>
}