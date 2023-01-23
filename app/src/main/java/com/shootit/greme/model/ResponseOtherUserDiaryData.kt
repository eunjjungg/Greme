package com.shootit.greme.model

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import java.sql.Timestamp

data class ResponseOtherUserDiaryData(
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("username")
    val username: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("hashtag")
    val hashtag: String,
    @SerializedName("createdDate")
    val createdDate: String,
    @SerializedName("challengeTitle")
    val challengeTitle: OtherUserData?
)
data class OtherUserData(
    @SerializedName("title")
    val title: String
)
