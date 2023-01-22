package com.shootit.greme.model

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class DiaryWriteData(
    @SerializedName("creationReq")
    val creationReq: WriteData?
)
data class WriteData(
    @SerializedName("content")
    val content: String,
    @SerializedName("hashtag")
    val hashtag: String,
    @SerializedName("challenge")
    val challenge: Long,
    @SerializedName("status")
    val status: Boolean
)
